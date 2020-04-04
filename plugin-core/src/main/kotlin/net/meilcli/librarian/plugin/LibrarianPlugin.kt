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
        const val generatePagesTask = "librarianGeneratePages"
    }

    override fun apply(project: Project) {
        val extension = project.extensions.create(librarianExtension, LibrarianExtension::class.java)
        val generatePipelineDependTasks = mutableListOf<Task>()

        project.createTask<ShowConfigurationsTask>("librarianShowConfigurations").apply {
            this.extension = extension
        }

        generatePipelineDependTasks += project.createTask<GenerateArtifactsTask>(generateArtifactsTask).apply {
            this.extension = extension
        }

        generatePipelineDependTasks += project.createTask<GenerateGroupsTask>(generateGroupsTask).apply {
            this.extension = extension
            mustRunAfter(generateArtifactsTask, "librarianGeneratePresetGroups")
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