package net.meilcli.librarian.plugin.internal

import net.meilcli.librarian.plugin.LibrarianDepth
import net.meilcli.librarian.plugin.entities.Artifact
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.artifacts.ResolvedDependency
import org.slf4j.LoggerFactory

class ArtifactLoader {

    data class Result(
        val entries: List<Entry>
    ) {
        data class Entry(
            val configurationName: String,
            val artifacts: List<Artifact>
        )
    }

    private class Context {

        private val artifacts = mutableMapOf<String, MutableList<Artifact>>()
        private val projects = mutableListOf<Project>()

        fun addProject(project: Project) {
            projects.add(project)
        }

        fun hasProject(project: Project): Boolean {
            return projects.contains(project)
        }

        fun addArtifact(configuration: Configuration, resolvedArtifact: ResolvedArtifact) {
            val configurationName = configuration.name
            val artifact = Artifact(
                resolvedArtifact.moduleVersion.id.group,
                resolvedArtifact.moduleVersion.id.name,
                resolvedArtifact.moduleVersion.id.version
            )

            val list = artifacts.getOrPut(configurationName) { mutableListOf() }
            if (list.contains(artifact)) {
                return
            }
            list.add(artifact)
        }

        fun result(): Result {
            return Result(artifacts.map { Result.Entry(it.key, it.value) })
        }
    }

    companion object {

        private const val unspecifiedVersion = "unspecified"
    }

    private val logger = LoggerFactory.getLogger(ArtifactLoader::class.java)

    fun load(project: Project, depth: LibrarianDepth): Result {
        val context = Context()
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
                    context.addArtifact(configuration, resolvedArtifact)
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