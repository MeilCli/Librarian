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
import net.meilcli.librarian.plugin.internal.libraries.LocalLibrariesLoader
import net.meilcli.librarian.plugin.internal.librarygroups.LocalLibraryGroupsLoader
import net.meilcli.librarian.plugin.internal.notices.LocalJsonNoticesWriter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

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

        for (noticeArtifact in configurationArtifacts.let { pageFiler.filter(it) }.let { artifactsTranslator.translate(it) }) {
            val foundArtifact = libraries.find { it.artifact == noticeArtifact.artifact }
            val foundGroup = libraryGroups.find { it.artifacts.contains(noticeArtifact.artifact) }
            val notice = when {
                foundGroup != null && foundArtifact != null -> {
                    val author = foundGroup.author ?: foundArtifact.author
                    val url = foundGroup.url ?: foundArtifact.url
                    val licenses = foundGroup.licenses ?: foundArtifact.licenses
                    if (foundArtifact.licenses.isNotEmpty()) {
                        // check same licenses
                        for (checkLicense in foundArtifact.licenses) {
                            if (checkLicense.name == Placeholder.licenseName || checkLicense.url == Placeholder.licenseUrl) {
                                // will override by group
                                continue
                            }
                            if (licenses.isEmpty()) {
                                // will override by group
                                continue
                            }
                            if (licenses.contains(checkLicense).not()) {
                                project.logger.warn("Librarian warning: group has not artifact license, ${foundGroup.name}, ${foundArtifact.artifact}")
                            }
                        }
                    }
                    Notice(
                        artifacts = foundGroup.artifacts,
                        name = foundGroup.name,
                        author = author,
                        url = url,
                        description = foundGroup.description,
                        licenses = licenses
                    )
                }
                foundArtifact != null -> {
                    Notice(
                        artifacts = listOf(foundArtifact.artifact),
                        name = foundArtifact.name,
                        author = foundArtifact.author,
                        url = foundArtifact.url,
                        description = foundArtifact.description,
                        licenses = foundArtifact.licenses
                    )
                }
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
            writeMarkdownPage(page, notices)
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

    private fun writeMarkdownPage(page: LibrarianPageExtension, notices: List<Notice>) {
        val extension = extension ?: return
        val sb = StringBuilder()
        sb.appendln("# ${page.title}")
        sb.appendln("*This markdown is auto generated by [Librarian](https://github.com/MeilCli/Librarian)*")
        sb.appendln()
        page.description?.also {
            sb.appendln(it)
            sb.appendln()
        }
        sb.appendln("## Using")
        for (notice in notices) {
            sb.appendln("- [${notice.name}](${notice.url}), licensed on ${notice.licenses.joinToString { "[${it.name}](${it.url})" }}, made by ${notice.author}")
        }

        val outputDirectory = File(project.rootProject.rootDir, "${extension.dataFolderName}/${page.name}")
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val outputFile = File(outputDirectory, page.markdownFileName)
        outputFile.writeText(sb.toString(), Charsets.UTF_8)
    }
}