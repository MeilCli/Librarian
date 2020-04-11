package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.IWriter
import net.meilcli.librarian.plugin.internal.LibrarianException
import net.meilcli.librarian.plugin.internal.Placeholder
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsByPageFilter
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsLoader
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsToArtifactsTranslator
import net.meilcli.librarian.plugin.internal.libraries.LibraryToNoticeTranslator
import net.meilcli.librarian.plugin.internal.libraries.LocalLibrariesLoader
import net.meilcli.librarian.plugin.internal.librarygroups.LibraryGroupToNoticeTranslator
import net.meilcli.librarian.plugin.internal.librarygroups.LocalLibraryGroupsLoader
import net.meilcli.librarian.plugin.internal.notices.LicenseOverrideNoticeValidator
import net.meilcli.librarian.plugin.internal.notices.LocalJsonNoticesWriter
import net.meilcli.librarian.plugin.internal.notices.LocalMarkdownNoticesWriter
import net.meilcli.librarian.plugin.internal.notices.NoticeOverride
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GeneratePagesTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @UnstableDefault
    @TaskAction
    fun action() {
        val extension = extension ?: return
        if (extension.pages.isEmpty()) {
            return
        }

        val librariesLoader = LocalLibrariesLoader(project)
        val libraryGroupsLoader = LocalLibraryGroupsLoader(project)
        val configurationArtifactsLoader = ConfigurationArtifactsLoader(project, extension)

        val configurationArtifacts = configurationArtifactsLoader.load()
        val libraries = librariesLoader.load()
        val libraryGroups = libraryGroupsLoader.load()

        for (page in extension.pages) {
            try {
                writePage(extension, page, configurationArtifacts, libraries, libraryGroups)
            } catch (exception: Exception) {
                if (exception is LibrarianException) {
                    throw exception
                }
                project.logger.error("Failed Librarian, page: ${page.name}", exception)
            }
        }
    }

    @UnstableDefault
    private fun writePage(
        extension: LibrarianExtension,
        page: LibrarianPageExtension,
        configurationArtifacts: List<ConfigurationArtifact>,
        libraries: List<Library>,
        libraryGroups: List<LibraryGroup>
    ) {
        val notices = mutableListOf<Notice>()
        val pageFiler = ConfigurationArtifactsByPageFilter(page)
        val artifactsTranslator = ConfigurationArtifactsToArtifactsTranslator()
        val libraryToNoticeTranslator = LibraryToNoticeTranslator()
        val libraryGroupToNoticeTranslator = LibraryGroupToNoticeTranslator()
        val noticeOverride = NoticeOverride()
        val overrideNoticeValidator = LicenseOverrideNoticeValidator()

        for (noticeArtifact in configurationArtifacts.let { pageFiler.filter(it) }.let { artifactsTranslator.translate(it) }) {
            val foundLibrary = libraries.find { it.artifact == noticeArtifact.artifact }
            val foundLibraryGroup = libraryGroups.find { it.artifacts.contains(noticeArtifact.artifact) }
            val notice = when {
                foundLibraryGroup != null && foundLibrary != null -> {
                    val libraryNotice = libraryToNoticeTranslator.translate(foundLibrary)
                    val libraryGroupNotice = libraryGroupToNoticeTranslator.translate(foundLibraryGroup)
                    if (overrideNoticeValidator.valid(libraryNotice, libraryGroupNotice).not()) {
                        project.logger.warn("Librarian warning: group has not artifact license, ${foundLibraryGroup.name}, ${foundLibrary.artifact}")
                    }
                    noticeOverride.override(libraryNotice, libraryGroupNotice)
                }
                foundLibrary != null -> libraryToNoticeTranslator.translate(foundLibrary)
                else -> {
                    throw LibrarianException("Librarian not found library data: ${noticeArtifact.group}:${noticeArtifact.name}")
                }
            }

            if (notices.contains(notice).not()) {
                notices.add(notice)
            }
        }

        reduceUnUseArtifact(notices, configurationArtifacts)
        checkNotice(notices)

        notices.sortBy { it.name }

        val writers = mutableListOf<IWriter<List<Notice>>>()

        if (page.markdown) {
            writers += LocalMarkdownNoticesWriter(project, extension, page)
        }
        if (page.json) {
            writers += LocalJsonNoticesWriter(project, extension, page)
        }

        for (writer in writers) {
            writer.write(notices)
        }
    }

    private fun checkNotice(notices: List<Notice>) {
        val extension = extension ?: return

        fun warnOrThrow(notice: Notice, name: String) {
            if (extension.failOnGeneratePageWhenFoundPlaceholder) {
                throw LibrarianException("Librarian error: notice has placeholder at $name, ${notice.artifacts.joinToString()}")
            } else {
                project.logger.warn("Librarian warning: notice has placeholder at $name, ${notice.artifacts.joinToString()}")
            }
        }

        for (notice in notices) {
            if (notice.author == Placeholder.author) {
                warnOrThrow(notice, "author")
            }
            if (notice.name == Placeholder.name) {
                warnOrThrow(notice, "name")
            }
            if (notice.url == Placeholder.url) {
                warnOrThrow(notice, "url")
            }
            for (license in notice.licenses) {
                if (license.name == Placeholder.name) {
                    warnOrThrow(notice, "license.name")
                }
                if (license.url == Placeholder.url) {
                    warnOrThrow(notice, "license.url")
                }
            }
            if (1 < notices.count { it.artifacts == notice.artifacts }) {
                project.logger.warn("Librarian warning: notice has duplication, ${notice.artifacts.joinToString()}")
            }
        }
    }

    private fun reduceUnUseArtifact(notices: MutableList<Notice>, configurationArtifacts: List<ConfigurationArtifact>) {
        for (i in 0 until notices.size) {
            val oldNotice = notices[i]
            val newNotice = Notice(
                oldNotice.artifacts
                    .filter { artifact ->
                        configurationArtifacts
                            .asSequence()
                            .flatMap { it.artifacts.asSequence() }
                            .any { it.artifact == artifact }
                    },
                name = oldNotice.name,
                author = oldNotice.author,
                url = oldNotice.url,
                description = oldNotice.description,
                licenses = oldNotice.licenses
            )
            notices[i] = newNotice
        }
    }
}