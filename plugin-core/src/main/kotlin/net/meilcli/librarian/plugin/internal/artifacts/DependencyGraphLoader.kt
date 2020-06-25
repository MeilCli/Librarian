package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianDepth
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.ConfigurationName
import net.meilcli.librarian.plugin.entities.DependencyGraph
import net.meilcli.librarian.plugin.internal.ILoader
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ModuleVersionIdentifier
import org.gradle.api.artifacts.ResolvedDependency
import org.slf4j.LoggerFactory

class DependencyGraphLoader(
    private val project: Project,
    private val depth: LibrarianDepth,
    private val ignoreArtifacts: List<String>
) : ILoader<DependencyGraph> {

    private class Context(private val ignoreArtifacts: List<String>) {

        private val projects = mutableListOf<Project>()
        private val results = mutableMapOf<Project, MutableMap<ConfigurationName, MutableList<DependencyGraph.Element>>>()

        fun addProject(project: Project) {
            projects.add(project)
        }

        fun hasProject(project: Project): Boolean {
            return projects.contains(project)
        }

        fun addArtifact(project: Project, configuration: Configuration, moduleVersionIdentifier: ModuleVersionIdentifier) {
            val artifact = Artifact(
                moduleVersionIdentifier.group,
                moduleVersionIdentifier.name,
                moduleVersionIdentifier.version
            )

            if (ignoreArtifacts.any { artifact.artifact.startsWith(it) }) {
                return
            }

            val element = DependencyGraph.Element.Artifact(artifact)
            val map = results.getOrPut(project) { mutableMapOf() }
            val list = map.getOrPut(ConfigurationName(configuration.name)) { mutableListOf() }
            if (list.contains(element)) {
                return
            }
            list.add(element)
        }

        fun addSubProject(project: Project, configuration: Configuration, subProject: Project) {
            val element = DependencyGraph.Element.Project(subProject)
            val map = results.getOrPut(project) { mutableMapOf() }
            val list = map.getOrPut(ConfigurationName(configuration.name)) { mutableListOf() }
            if (list.contains(element)) {
                return
            }
            list.add(element)
        }

        fun result(): DependencyGraph {
            return DependencyGraph(results)
        }
    }

    companion object {

        private const val unspecifiedVersion = "unspecified"
    }

    private val logger = LoggerFactory.getLogger(DependencyGraphLoader::class.java)

    override fun load(): DependencyGraph {
        val context = Context(ignoreArtifacts)

        loadResolvedArtifacts(project, context, depth)

        return context.result()
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

                // in Gradle 6.0, some artifact is skipped resolvedArtifacts, so add resolvedDependency
                context.addArtifact(project, configuration, resolvedDependency.module.id)
                for (resolvedArtifact in resolvedArtifacts) {
                    context.addArtifact(project, configuration, resolvedArtifact.moduleVersion.id)
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