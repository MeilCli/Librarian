package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.*
import net.meilcli.librarian.plugin.internal.*
import net.meilcli.librarian.plugin.internal.artifacts.*
import net.meilcli.librarian.plugin.internal.libraries.LibrariesToNoticeTranslator
import net.meilcli.librarian.plugin.internal.libraries.LibraryToNoticeTranslator
import net.meilcli.librarian.plugin.internal.libraries.LocalLibrariesLoader
import net.meilcli.librarian.plugin.internal.librarygroups.LibraryGroupToNoticeTranslator
import net.meilcli.librarian.plugin.internal.librarygroups.LocalLibraryGroupsLoader
import net.meilcli.librarian.plugin.internal.notices.*
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

        val dependencyGraphLoader = DependencyGraphLoader(project, extension.depthType, extension.ignoreArtifacts)
        val dependencyGraph = dependencyGraphLoader.load()
        val dependencyGraphValidator = DependencyGraphValidator(project, extension)

        if (dependencyGraphValidator.valid(dependencyGraph).not()) {
            throw LibrarianException("Librarian encounter too many configurations. please filter page.configurations")
        }

        val dependencyGraphToConfigurationArtifactsTranslator = DependencyGraphToConfigurationArtifactsTranslator(project, extension)
        val configurationArtifacts = dependencyGraphToConfigurationArtifactsTranslator.translate(dependencyGraph)

        val librariesLoader = LocalLibrariesLoader(project)
        val libraryGroupsLoader = LocalLibraryGroupsLoader(project)
        val artifactsTranslator = ConfigurationArtifactsToArtifactsTranslator()
        val noticesAggregator = ArtifactsToNoticesAggregator(
            extension,
            LibraryToNoticeTranslator(),
            LibrariesToNoticeTranslator(),
            LibraryGroupToNoticeTranslator(),
            NoticeOverride(),
            LicenseOverrideNoticeValidator()
        )
        val sortTranslator = NoticesBySortTranslator()

        val libraries = librariesLoader.load()
        val libraryGroups = libraryGroupsLoader.load()

        for (page in extension.pages) {
            try {
                writePage(
                    extension,
                    page,
                    configurationArtifacts,
                    libraries,
                    libraryGroups,
                    artifactsTranslator,
                    noticesAggregator,
                    sortTranslator
                )
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
        configurationArtifacts: List<ConfigurationArtifacts>,
        libraries: List<Library>,
        libraryGroups: List<LibraryGroup>,
        artifactsTranslator: ITranslator<List<ConfigurationArtifacts>, Set<Artifact>>,
        noticesAggregator: IAggregator3<Collection<Artifact>, List<Library>, List<LibraryGroup>, List<Notice>>,
        sortTranslator: ITranslator<List<Notice>, List<Notice>>
    ) {
        val pageFiler = ConfigurationArtifactsByPageFilter(page)

        val notices = configurationArtifacts.let { pageFiler.filter(it) }
            .let { artifactsTranslator.translate(it) }
            .let { noticesAggregator.aggregate(it, libraries, libraryGroups) }
            .let { it + page.additionalNotices.map { it.toNotice() } }
            .let { sortTranslator.translate(it) }

        checkNotice(notices)

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
                throw LibrarianException("Librarian error: notice has placeholder at $name, ${notice.resources.flatMap { it.artifacts }
                    .joinToString()}")
            } else {
                project.logger.warn("Librarian warning: notice has placeholder at $name, ${notice.resources.flatMap { it.artifacts }
                    .joinToString()}")
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
            for (license in notice.resources.flatMap { it.licenses }) {
                if (license.name == Placeholder.name) {
                    warnOrThrow(notice, "license.name")
                }
                if (license.url == Placeholder.url) {
                    warnOrThrow(notice, "license.url")
                }
            }
        }
    }
}