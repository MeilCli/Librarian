package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianDepth
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.internal.ILoader
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.artifacts.ResolvedDependency
import org.slf4j.LoggerFactory

class ConfigurationArtifactsLoader(
    private val project: Project,
    private val depth: LibrarianDepth,
    private val ignoreArtifacts: List<String>
) : ILoader<Sequence<ConfigurationArtifact>> {

    private sealed class Result {

        data class SubProject(val project: Project) : Result()
        data class Artifact(val artifact: net.meilcli.librarian.plugin.entities.Artifact) : Result()
    }

    private class Context(private val ignoreArtifacts: List<String>) {

        private val projects = mutableListOf<Project>()
        private val results = mutableMapOf<Project, MutableMap<String, MutableList<Result>>>()

        fun addProject(project: Project) {
            projects.add(project)
        }

        fun hasProject(project: Project): Boolean {
            return projects.contains(project)
        }

        fun addArtifact(project: Project, configuration: Configuration, resolvedArtifact: ResolvedArtifact) {
            val artifact = Artifact(
                resolvedArtifact.moduleVersion.id.group,
                resolvedArtifact.moduleVersion.id.name,
                resolvedArtifact.moduleVersion.id.version
            )

            if (ignoreArtifacts.any { artifact.artifact.startsWith(it) }) {
                return
            }

            val element = Result.Artifact(artifact)
            val map = results.getOrPut(project) { mutableMapOf() }
            val list = map.getOrPut(configuration.name) { mutableListOf() }
            if (list.contains(element)) {
                return
            }
            list.add(element)
        }

        fun addSubProject(project: Project, configuration: Configuration, subProject: Project) {
            val element = Result.SubProject(subProject)
            val map = results.getOrPut(project) { mutableMapOf() }
            val list = map.getOrPut(configuration.name) { mutableListOf() }
            if (list.contains(element)) {
                return
            }
            list.add(element)
        }

        fun result(rootProject: Project): Sequence<ConfigurationArtifact> {
            val graph = mutableListOf<List<Pair<Project, String>>>()

            aggregateDependencyGraph(rootProject, graph, emptyList())

            return graph
                .groupBy { x -> x.map { y -> y.second } }
                .asSequence()
                .map { x ->
                    ConfigurationArtifact(
                        x.key,
                        x.value.flatMap { y -> resolveConfiguration(y.last().first, y.last().second) }.distinct()
                    )
                }
        }

        private fun aggregateDependencyGraph(
            project: Project,
            graph: MutableList<List<Pair<Project, String>>>,
            parentGraph: List<Pair<Project, String>>
        ) {
            for (map in results.getOrDefault(project, mutableMapOf())) {
                graph.add(parentGraph + listOf(Pair(project, map.key)))
                for (element in map.value) {
                    if (element !is Result.SubProject) {
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
            configuration: String
        ): List<Artifact> {
            val result = mutableListOf<Artifact>()

            for (element in results.getOrDefault(project, mutableMapOf()).getOrDefault(configuration, mutableListOf())) {
                if (element is Result.Artifact) {
                    result += element.artifact
                }
            }

            return result
        }
    }

    companion object {

        private const val unspecifiedVersion = "unspecified"
    }

    private val logger = LoggerFactory.getLogger(ConfigurationArtifactsLoader::class.java)

    override fun load(): Sequence<ConfigurationArtifact> {
        val context = Context(ignoreArtifacts)

        loadResolvedArtifacts(project, context, depth)

        return context.result(project)
    }

    private fun loadResolvedArtifacts(
        project: Project,
        context: Context,
        depth: LibrarianDepth
    ) {
        if (context.hasProject(project)) {
            // already had loaded
            return
        }
        context.addProject(project)
        for (configuration in project.configurations) {
            loadResolvedArtifacts(project, configuration, context, depth)
        }
        for (configuration in project.buildscript.configurations) {
            loadResolvedArtifacts(project, configuration, context, depth)
        }
        for (configuration in project.rootProject.buildscript.configurations) {
            loadResolvedArtifacts(project, configuration, context, depth)
        }
    }

    private fun loadResolvedArtifacts(
        project: Project,
        configuration: Configuration,
        context: Context,
        depth: LibrarianDepth
    ) {
        if (configuration.isCanBeResolved.not()) {
            logger.debug("${configuration.name} is not canBeResolved")
            return
        }

        val resolvedDependencies = configuration.resolvedConfiguration
            .lenientConfiguration
            .firstLevelModuleDependencies
        try {
            loadResolvedArtifacts(project, configuration, resolvedDependencies, context, depth)
        } catch (exception: Exception) {
            logger.warn("Failed Librarian", exception)
        }
    }

    private fun loadResolvedArtifacts(
        project: Project,
        configuration: Configuration,
        resolvedDependencies: Set<ResolvedDependency>,
        context: Context,
        depth: LibrarianDepth
    ) {
        for (resolvedDependency in resolvedDependencies) {
            if (resolvedDependency.moduleVersion == unspecifiedVersion) {
                val subProject = resolvedDependency.getSubProject(project)
                if (subProject != null) {
                    context.addSubProject(project, configuration, subProject)
                    loadResolvedArtifacts(subProject, context, depth)
                } else {
                    loadResolvedArtifacts(project, configuration, resolvedDependency.children, context, depth)
                }
            } else {
                val resolvedArtifacts = when (depth) {
                    LibrarianDepth.FirstLevel -> resolvedDependency.moduleArtifacts
                    LibrarianDepth.AllLevel -> resolvedDependency.allModuleArtifacts
                }
                for (resolvedArtifact in resolvedArtifacts) {
                    context.addArtifact(project, configuration, resolvedArtifact)
                }
            }
        }
    }

    private fun ResolvedDependency.getSubProject(project: Project): Project? {
        if (moduleVersion != unspecifiedVersion) {
            return null
        }
        val rootProjectName = project.rootProject.name
        if (moduleGroup == rootProjectName) {
            // child project
            return project.findProject(":$moduleName")
        }
        if (moduleGroup.startsWith("${rootProjectName}.")) {
            // nested child
            val name = moduleGroup.replaceFirst("${rootProjectName}.", "").replace('.', ':') + ":" + moduleName
            return project.findProject(":$name")
        }
        return null
    }
}