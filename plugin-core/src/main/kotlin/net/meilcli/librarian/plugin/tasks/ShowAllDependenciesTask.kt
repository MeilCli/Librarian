package net.meilcli.librarian.plugin.tasks

import net.meilcli.librarian.plugin.LibrarianDepth
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.DependencyGraph
import net.meilcli.librarian.plugin.internal.artifacts.DependencyGraphLoader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class ShowAllDependenciesTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @TaskAction
    fun action() {
        val extension = extension ?: return

        val dependencyGraphLoader = DependencyGraphLoader(project, LibrarianDepth.AllLevel, extension.ignoreArtifacts)
        val dependencyGraph = dependencyGraphLoader.load()
        val artifacts = dependencyGraph.graph
            .asSequence()
            .flatMap { it.value.values.asSequence() }
            .flatten()
            .filterIsInstance<DependencyGraph.Element.Artifact>()
            .map { it.artifact.artifact }
            .distinct()
            .sortedBy { it }

        project.logger.quiet("dependencies")
        for (artifact in artifacts) {
            project.logger.quiet(artifact)
        }
    }
}