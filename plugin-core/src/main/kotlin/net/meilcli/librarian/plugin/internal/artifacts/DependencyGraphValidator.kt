package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.configurations.ContainConfiguration
import net.meilcli.librarian.plugin.configurations.ExactConfiguration
import net.meilcli.librarian.plugin.entities.DependencyGraph
import net.meilcli.librarian.plugin.internal.IValidator1
import org.gradle.api.Project

class DependencyGraphValidator(
    private val project: Project,
    private val extension: LibrarianExtension
) : IValidator1<DependencyGraph> {

    private class ProjectData(val configurationCount: Int, val linkedProjects: List<Project>)

    override fun valid(element: DependencyGraph): Boolean {
        val configurationNames = aggregateConfigurationNames()
        val projects = mutableMapOf<Project, ProjectData>()

        for (projectMap in element.graph) {
            val count = projectMap.value
                .keys
                .filter { configurationNames.contains(it.name) }
                .size

            val linkedProjects = mutableListOf<Project>()
            for (dependencyElement in projectMap.value.values.flatten()) {
                if (dependencyElement !is DependencyGraph.Element.Project) {
                    continue
                }
                if (linkedProjects.contains(dependencyElement.project)) {
                    continue
                }
                linkedProjects += dependencyElement.project
            }

            projects[projectMap.key] = ProjectData(count, linkedProjects)
        }

        return projects.calculateConfigurations(project) < extension.failOnTooManyResolvingConfigurationLimit
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

    private fun Map<Project, ProjectData>.calculateConfigurations(project: Project, calculatedProject: List<Project> = emptyList()): Int {
        val projectData = this[project] ?: return 0

        var linkedProjectConfigurationCount = 0
        if (calculatedProject.contains(project).not()) {
            // when looping, it will be Android dynamic feature module
            // allow circular references only once
            // but placed a branch here to allow no more
            for (linkedProject in projectData.linkedProjects) {
                linkedProjectConfigurationCount += this.calculateConfigurations(linkedProject, calculatedProject + project)
            }
        }

        return if (0 < linkedProjectConfigurationCount) {
            projectData.configurationCount * linkedProjectConfigurationCount
        } else {
            projectData.configurationCount
        }
    }
}