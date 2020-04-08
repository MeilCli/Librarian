package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.internal.ArtifactLoader
import net.meilcli.librarian.plugin.internal.IPomProjectLoader
import net.meilcli.librarian.plugin.internal.MavenPomProjectLoader
import net.meilcli.librarian.plugin.internal.PomProjectTranslator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

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

        val artifactLoaderResult = ArtifactLoader().load(project, extension)
        val pomLoader = MavenPomProjectLoader()

        for (page in extension.pages) {
            try {
                loadDependency(pomLoader, artifactLoaderResult, page)
            } catch (exception: Exception) {
                project.logger.error("Failed Librarian, page: ${page.name}", exception)
            }
        }
    }

    @UnstableDefault
    private fun loadDependency(
        pomProjectLoader: IPomProjectLoader,
        artifactLoaderResult: ArtifactLoader.Result,
        page: LibrarianPageExtension
    ) {
        val queue = mutableSetOf<Artifact>()

        for (configuration in page.configurations) {
            val artifactResult = artifactLoaderResult.entries.firstOrNull { it.configurationName == configuration }
            if (artifactResult == null) {
                project.logger.warn("Librarian cannot resolve unknown configuration: $configuration")
                continue
            }
            queue.addAll(artifactResult.artifacts)
        }

        val results = queue.asSequence()
            .map {
                val result = pomProjectLoader.load(project, it)
                if (result == null) {
                    project.logger.warn("Librarian cannot found pom: ${it.group}:${it.name}:${it.version}")
                }
                result
            }
            .filterNotNull()
            .map { PomProjectTranslator.translate(it) }
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

        val outputDirectory = File(project.buildDir, "${LibrarianExtension.buildFolder}/${LibrarianExtension.artifactsFolder}")
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val json = Json {
            this.prettyPrint = true
        }

        for (result in results) {
            val outputFile = File(outputDirectory, "${result.artifact.replace(':', '-')}.json")
            val text = json.stringify(Library.serializer(), result)
            outputFile.writeText(text, Charsets.UTF_8)
        }
    }
}