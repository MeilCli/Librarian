package net.meilcli.librarian.plugin.tasks

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.internal.artifacts.DependencyGraphLoader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class ShowConfigurationsTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @TaskAction
    fun action() {
        val extension = extension ?: return

        val dependencyGraphLoader = DependencyGraphLoader(project, extension.depthType, extension.ignoreArtifacts)
        val dependencyGraph = dependencyGraphLoader.load()
        val configurationNames = dependencyGraph.graph
            .asSequence()
            .flatMap { it.value.keys.asSequence() }
            .map { it.name }
            .distinct()
            .sorted()

        project.logger.quiet("Configurations")
        for (configurationName in configurationNames) {
            project.logger.quiet(configurationName)
        }
    }
}