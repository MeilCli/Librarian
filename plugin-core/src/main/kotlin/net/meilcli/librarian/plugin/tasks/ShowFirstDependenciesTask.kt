package net.meilcli.librarian.plugin.tasks

import net.meilcli.librarian.plugin.LibrarianDepth
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsLoader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class ShowFirstDependenciesTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @TaskAction
    fun action() {
        val extension = extension ?: return

        val configurationArtifactsLoader = ConfigurationArtifactsLoader(project, LibrarianDepth.FirstLevel, extension.ignoreArtifacts)

        val result = configurationArtifactsLoader.load()
        project.logger.quiet("dependencies")
        for (artifact in result.flatMap { it.artifacts.asSequence() }.map { it.artifact }.distinct().sortedBy { it }) {
            project.logger.quiet(artifact)
        }
    }
}