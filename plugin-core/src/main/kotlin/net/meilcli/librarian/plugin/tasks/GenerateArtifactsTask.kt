package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.PomProject
import net.meilcli.librarian.plugin.internal.IParameterizedLoader
import net.meilcli.librarian.plugin.internal.artifacts.ArtifactsToPomProjectsTranslator
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsByPageFilter
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsLoader
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsToArtifactsTranslator
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
        val pomLoader = MavenPomProjectLoader(project)

        for (page in extension.pages) {
            try {
                loadDependency(pomLoader, configurationArtifactLoader.load(), page)
            } catch (exception: Exception) {
                project.logger.error("Failed Librarian, page: ${page.name}", exception)
            }
        }
    }

    @UnstableDefault
    private fun loadDependency(
        pomProjectLoader: IParameterizedLoader<Artifact, PomProject?>,
        configurationArtifacts: List<ConfigurationArtifact>,
        page: LibrarianPageExtension
    ) {
        val pageFilter = ConfigurationArtifactsByPageFilter(page)
        val artifactsTranslator = ConfigurationArtifactsToArtifactsTranslator()
        val pomProjectsTranslator = ArtifactsToPomProjectsTranslator(pomProjectLoader)
        val libraryTranslator = PomProjectToLibraryTranslator()

        val results = configurationArtifacts.let { pageFilter.filter(it) }
            .let { artifactsTranslator.translate(it) }
            .let { pomProjectsTranslator.translate(it) }
            .asSequence()
            .map { libraryTranslator.translate(it) }
            .groupBy { it.artifact }
            .map {
                val library = it.value.first() // ToDo: Priority
                Library(
                    artifact = library.artifact,
                    name = library.name,
                    author = library.author,
                    url = library.url,
                    description = library.description,
                    licenses = it.value.flatMap { x -> x.licenses }.distinct()
                )
            }

        val libraryWriter = LocalLibrariesWriter(project)
        libraryWriter.write(results)
    }
}