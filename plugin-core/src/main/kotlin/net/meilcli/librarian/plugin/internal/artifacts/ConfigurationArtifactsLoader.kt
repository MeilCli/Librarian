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
) : ILoader<List<ConfigurationArtifact>> {

    private sealed class Result {

        data class SubProject(val configuration: String, val project: Project) : Result()
        data class Artifact(val configuration: String, val artifact: net.meilcli.librarian.plugin.entities.Artifact) : Result()
    }

    private class Context(private val ignoreArtifacts: List<String>) {

        private val projects = mutableListOf<Project>()
        private val results = mutableMapOf<Project, MutableList<Result>>()

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

            val element = Result.Artifact(configuration.name, artifact)
            val list = results.getOrPut(project) { mutableListOf() }
            if (list.contains(element)) {
                return
            }
            list.add(element)
        }

        fun addSubProject(project: Project, configuration: Configuration, subProject: Project) {
            val element = Result.SubProject(configuration.name, subProject)
            val list = results.getOrPut(project) { mutableListOf() }
            if (list.contains(element)) {
                return
            }
            list.add(element)
        }

        fun result(rootProject: Project): List<ConfigurationArtifact> {
            return aggregateResult(rootProject, emptyList())
                .groupBy { x -> x.first.map { y -> y.second } }
                .map { x -> ConfigurationArtifact(x.key, x.value.map { y -> y.second }) }
        }

        private fun aggregateResult(
            project: Project,
            parentConfigurations: List<Pair<String, String>>
        ): List<Pair<List<Pair<String, String>>, Artifact>> {
            return results.getOrDefault(project, mutableListOf()).flatMap {
                when (it) {
                    is Result.SubProject -> {
                        if (parentConfigurations.any { x -> x.first == it.project.projectDir.path }) {
                            // break infinite loop
                            emptyList()
                        } else {
                            aggregateResult(
                                it.project,
                                parentConfigurations + listOf(Pair(project.projectDir.path, it.configuration))
                            )
                        }
                    }
                    is Result.Artifact -> listOf(
                        Pair(
                            parentConfigurations + listOf(Pair(project.projectDir.path, it.configuration)),
                            it.artifact
                        )
                    )
                }
            }
        }
    }

    companion object {

        private const val unspecifiedVersion = "unspecified"
    }

    private val logger = LoggerFactory.getLogger(ConfigurationArtifactsLoader::class.java)

    override fun load(): List<ConfigurationArtifact> {
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