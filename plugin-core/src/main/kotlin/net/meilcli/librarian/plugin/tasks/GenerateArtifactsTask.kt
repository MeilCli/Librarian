package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.PomProject
import net.meilcli.librarian.plugin.internal.IAggregator
import net.meilcli.librarian.plugin.internal.ITranslator
import net.meilcli.librarian.plugin.internal.IWriter
import net.meilcli.librarian.plugin.internal.artifacts.*
import net.meilcli.librarian.plugin.internal.libraries.LocalLibrariesWriter
import net.meilcli.librarian.plugin.internal.pomprojects.MavenPomProjectLoader
import net.meilcli.librarian.plugin.internal.pomprojects.PomProjectToLibraryTranslator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GenerateArtifactsTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @UnstableDefault
    @TaskAction
    fun action() {
        val extension = extension ?: return
        if (extension.pages.isEmpty()) {
            return
        }

        val configurationArtifactLoader = ConfigurationArtifactsLoader(project, extension)
        val configurationArtifacts = configurationArtifactLoader.load()
        val artifactsTranslator = ConfigurationArtifactsToArtifactsTranslator()
        val pomProjectsTranslator = ArtifactsToPomProjectsTranslator(MavenPomProjectLoader(project))
        val librariesAggregator = PomProjectsToLibrariesAggregator(PomProjectToLibraryTranslator())
        val librariesWriter = LocalLibrariesWriter(project)

        for (page in extension.pages) {
            try {
                executePage(
                    page,
                    configurationArtifacts,
                    artifactsTranslator,
                    pomProjectsTranslator,
                    librariesAggregator,
                    librariesWriter
                )
            } catch (exception: Exception) {
                project.logger.error("Failed Librarian, page: ${page.name}", exception)
            }
        }
    }

    private fun executePage(
        page: LibrarianPageExtension,
        configurationArtifacts: List<ConfigurationArtifact>,
        artifactsTranslator: ITranslator<List<ConfigurationArtifact>, Set<Artifact>>,
        pomProjectsTranslator: ITranslator<Collection<Artifact>, List<PomProject>>,
        librariesAggregator: IAggregator<List<PomProject>, List<Library>>,
        librariesWriter: IWriter<List<Library>>
    ) {
        val pageFilter = ConfigurationArtifactsByPageFilter(page)

        val results = configurationArtifacts.let { pageFilter.filter(it) }
            .let { artifactsTranslator.translate(it) }
            .let { pomProjectsTranslator.translate(it) }
            .let { librariesAggregator.aggregate(it) }

        librariesWriter.write(results)
    }
}