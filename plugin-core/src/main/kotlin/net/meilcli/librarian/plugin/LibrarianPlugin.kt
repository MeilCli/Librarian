package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.tasks.GenerateGroupsTask
import net.meilcli.librarian.plugin.tasks.GeneratePagesTask
import net.meilcli.librarian.plugin.tasks.LoadArtifactsTask
import net.meilcli.librarian.plugin.tasks.ShowConfigurationsTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File

open class LibrarianPlugin : Plugin<Project> {

    companion object {

        private const val librarianGroup = "librarian"
        private const val librarianExtension = "librarian"
    }

    override fun apply(project: Project) {
        val extension = project.extensions.create(librarianExtension, LibrarianExtension::class.java)

        project.createTask<ShowConfigurationsTask>("librarianShowConfigurations").apply {
            this.extension = extension
        }

        project.createTask<LoadArtifactsTask>("librarianLoadArtifacts").apply {
            this.extension = extension
        }

        project.createTask<GenerateGroupsTask>("librarianGenerateGroups").apply {
            this.extension = extension
        }

        project.createTask<GeneratePagesTask>("librarianGeneratePages").apply {
            this.extension = extension
        }

        val loadDependenciesTask =
            project.tasks.create("loadDependencies", LoadDependenciesTask::class.java)
        //loadDependenciesTask.configurations = project.configurations
        loadDependenciesTask.outputFile = File(project.buildDir, "generated/librarian/test.txt")
    }

    private inline fun <reified T : Task> Project.createTask(name: String): T {
        val task = tasks.create(name, T::class.java)
        task.group = librarianGroup
        return task
    }
}