package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.configurations.ContainConfiguration
import net.meilcli.librarian.plugin.configurations.ExactConfiguration
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.ConfigurationArtifacts
import net.meilcli.librarian.plugin.entities.ConfigurationName
import net.meilcli.librarian.plugin.entities.DependencyGraph
import net.meilcli.librarian.plugin.internal.ITranslator
import org.gradle.api.Project

class DependencyGraphToConfigurationArtifactsTranslator(
    private val project: Project,
    private val extension: LibrarianExtension
) : ITranslator<DependencyGraph, List<ConfigurationArtifacts>> {

    private class Aggregator(
        private val dependencyGraph: DependencyGraph,
        private val needConfigurationNames: Set<String>
    ) {

        fun result(rootProject: Project): List<ConfigurationArtifacts> {
            val graph = mutableListOf<List<Pair<Project, ConfigurationName>>>()

            aggregateDependencyGraph(rootProject, graph, emptyList())

            return graph
                .groupBy { x -> x.map { y -> y.second } }
                .map { x ->
                    ConfigurationArtifacts(
                        x.key,
                        x.value.flatMap { y -> resolveConfiguration(y.last().first, y.last().second) }.distinct()
                    )
                }
        }

        private fun aggregateDependencyGraph(
            project: Project,
            graph: MutableList<List<Pair<Project, ConfigurationName>>>,
            parentGraph: List<Pair<Project, ConfigurationName>>
        ) {
            for (map in dependencyGraph.graph.getOrDefault(project, mutableMapOf())) {
                if (needConfigurationNames.contains(map.key.name).not()) {
                    continue
                }

                graph.add(parentGraph + listOf(Pair(project, map.key)))

                for (element in map.value) {
                    if (element !is DependencyGraph.Element.Project) {
                        continue
                    }
                    if (parentGraph.any { it.first == project }) {
                        // escape infinite loop
                        continue
                    }
                    aggregateDependencyGraph(element.project, graph, parentGraph + listOf(Pair(project, map.key)))
                }
            }
        }

        private fun resolveConfiguration(
            project: Project,
            configuration: ConfigurationName
        ): List<Artifact> {
            val result = mutableListOf<Artifact>()

            for (element in dependencyGraph.graph.getOrDefault(project, mutableMapOf()).getOrDefault(configuration, mutableListOf())) {
                if (element is DependencyGraph.Element.Artifact) {
                    result += element.artifact
                }
            }

            return result
        }
    }

    override fun translate(source: DependencyGraph): List<ConfigurationArtifacts> {
        val needConfigurationNames = aggregateConfigurationNames()
        val aggregator = Aggregator(source, needConfigurationNames)

        return aggregator.result(project)
    }

    private fun aggregateConfigurationNames(): Set<String> {
        val result = mutableSetOf<String>()

        for (page in extension.pages) {
            for (configuration in page.configurations.value) {
                result += when (configuration) {
                    is ExactConfiguration -> configuration.value
                    is ContainConfiguration -> configuration.value
                    else -> emptyList()
                }
            }
        }

        return result
    }
}