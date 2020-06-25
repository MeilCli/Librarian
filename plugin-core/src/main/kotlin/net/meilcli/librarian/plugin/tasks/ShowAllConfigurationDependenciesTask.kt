package net.meilcli.librarian.plugin.tasks

import net.meilcli.librarian.plugin.LibrarianDepth
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.DependencyGraph
import net.meilcli.librarian.plugin.internal.artifacts.DependencyGraphLoader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class ShowAllConfigurationDependenciesTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @TaskAction
    fun action() {
        val extension = extension ?: return

        val dependencyGraphLoader = DependencyGraphLoader(project, LibrarianDepth.AllLevel, extension.ignoreArtifacts)
        val dependencyGraph = dependencyGraphLoader.load()

        val configurationDependencies = mutableMapOf<String, MutableList<String>>()

        for ((project, configurationMap) in dependencyGraph.graph) {
            for ((configuration, elements) in configurationMap) {
                for (element in elements) {
                    if (element !is DependencyGraph.Element.Artifact) {
                        continue
                    }
                    val list = configurationDependencies.getOrPut(element.artifact.artifact) { mutableListOf() }
                    list += ":${project.name}:${configuration.name}"
                }
            }
        }

        project.logger.quiet("dependencies")
        for (configurationDependency in configurationDependencies.toSortedMap()) {
            project.logger.quiet("${configurationDependency.key}: ${configurationDependency.value.distinct().joinToString()}")
        }
    }
}