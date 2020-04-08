package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.PomProject
import net.meilcli.librarian.plugin.internal.IParameterizedLoader
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactLoader
import net.meilcli.librarian.plugin.internal.libraries.LocalLibraryWriter
import net.meilcli.librarian.plugin.internal.pomprojects.LibraryTranslator
import net.meilcli.librarian.plugin.internal.pomprojects.MavenPomProjectLoader
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

        val configurationArtifactLoader = ConfigurationArtifactLoader(project, extension)
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
        val queue = mutableSetOf<Artifact>()

        for (configuration in page.configurations) {
            val configurationArtifact = configurationArtifacts.firstOrNull { it.configurationName == configuration }
            if (configurationArtifact == null) {
                project.logger.warn("Librarian cannot resolve unknown configuration: $configuration")
                continue
            }
            queue.addAll(configurationArtifact.artifacts)
        }

        val libraryTranslator = LibraryTranslator()

        val results = queue.asSequence()
            .map {
                val result = pomProjectLoader.load(it)
                if (result == null) {
                    project.logger.warn("Librarian cannot found pom: ${it.group}:${it.name}:${it.version}")
                }
                result
            }
            .filterNotNull()
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

        val libraryWriter = LocalLibraryWriter(project)
        libraryWriter.write(results)
    }
}