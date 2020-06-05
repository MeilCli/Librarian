package net.meilcli.librarian.plugin.entities

import org.gradle.api.Project

data class DependencyGraph(val graph: Map<Project, Map<ConfigurationName, List<Element>>>) {

    sealed class Element {

        data class Project(val project: org.gradle.api.Project) : Element()
        data class Artifact(val artifact: net.meilcli.librarian.plugin.entities.Artifact) : Element()
    }
}