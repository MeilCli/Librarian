package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.extensions.createTask
import net.meilcli.librarian.plugin.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

open class LibrarianPlugin : Plugin<Project> {

    companion object {

        private const val librarianExtension = "librarian"
        const val generateArtifactsTask = "librarianGenerateArtifacts"
        const val generateGroupsTask = "librarianGenerateGroups"
        const val generateBintrayGroupsTask = "librarianGenerateBintrayGroups"
        const val generatePagesTask = "librarianGeneratePages"
    }

    override fun apply(project: Project) {
        val extension = project.extensions.create(librarianExtension, LibrarianExtension::class.java, project)
        val generatePipelineDependTasks = mutableListOf<Task>()

        project.createTask<ShowConfigurationsTask>("librarianShowConfigurations").apply {
            this.extension = extension
        }

        project.createTask<ShowModuleConfigurationsTask>("librarianShowModuleConfigurations").apply {
            this.extension = extension
        }

        project.createTask<ShowFirstDependenciesTask>("librarianShowFirstDependencies").apply {
            this.extension = extension
        }

        project.createTask<ShowAllDependenciesTask>("librarianShowAllDependencies").apply {
            this.extension = extension
        }

        project.createTask<ShowFirstConfigurationDependenciesTask>("librarianShowFirstConfigurationDependencies").apply {
            this.extension = extension
        }

        project.createTask<ShowAllConfigurationDependenciesTask>("librarianShowAllConfigurationDependencies").apply {
            this.extension = extension
        }

        project.createTask<ShowFilteredDependencyGraphTask>("librarianShowFilteredDependencyGraph").apply {
            this.extension = extension
        }

        generatePipelineDependTasks += project.createTask<GenerateArtifactsTask>(generateArtifactsTask).apply {
            this.extension = extension
        }

        generatePipelineDependTasks += project.createTask<GenerateBintrayGroupsTask>(generateBintrayGroupsTask).apply {
            this.extension = extension
            mustRunAfter(generateArtifactsTask, "librarianGeneratePresetGroups")
        }

        generatePipelineDependTasks += project.createTask<GenerateGroupsTask>(generateGroupsTask).apply {
            this.extension = extension
            mustRunAfter(generateArtifactsTask, generateBintrayGroupsTask, "librarianGeneratePresetGroups")
        }

        generatePipelineDependTasks += project.createTask<GeneratePagesTask>(generatePagesTask).apply {
            this.extension = extension
            mustRunAfter(generateGroupsTask)
        }

        project.createTask<GeneratePipelineTask>("librarianGeneratePipeline").apply {
            setDependsOn(generatePipelineDependTasks)
        }
    }
}