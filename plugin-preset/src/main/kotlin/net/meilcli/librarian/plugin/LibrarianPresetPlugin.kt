package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.extensions.createTask
import net.meilcli.librarian.plugin.tasks.GeneratePresetGroupsTask
import net.meilcli.librarian.plugin.tasks.GeneratePresetPipelineTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class LibrarianPresetPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = checkNotNull(project.extensions.findByType(LibrarianExtension::class.java)) { "must apply plugin librarian" }

        val generatePresetGroupsTask = project.createTask<GeneratePresetGroupsTask>("librarianGeneratePresetGroups").apply {
            this.extension = extension
        }

        project.createTask<GeneratePresetPipelineTask>("librarianGeneratePresetPipeline").apply {
            val depends = mutableListOf<Task>()
            depends += checkNotNull(project.tasks.findByName(LibrarianPlugin.generateArtifactsTask))
            depends += generatePresetGroupsTask
            depends += checkNotNull(project.tasks.findByName(LibrarianPlugin.generateGroupsTask))
            depends += checkNotNull(project.tasks.findByName(LibrarianPlugin.generatePagesTask))
        }
    }
}