package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.extensions.createTask
import net.meilcli.librarian.plugin.tasks.GenerateArtifactsTask
import net.meilcli.librarian.plugin.tasks.GenerateGroupsTask
import net.meilcli.librarian.plugin.tasks.GeneratePagesTask
import net.meilcli.librarian.plugin.tasks.ShowConfigurationsTask
import org.gradle.api.Plugin
import org.gradle.api.Project

open class LibrarianPlugin : Plugin<Project> {

    companion object {

        private const val librarianExtension = "librarian"
    }

    override fun apply(project: Project) {
        val extension = project.extensions.create(librarianExtension, LibrarianExtension::class.java)

        project.createTask<ShowConfigurationsTask>("librarianShowConfigurations").apply {
            this.extension = extension
        }

        project.createTask<GenerateArtifactsTask>("librarianGenerateArtifacts").apply {
            this.extension = extension
        }

        project.createTask<GenerateGroupsTask>("librarianGenerateGroups").apply {
            this.extension = extension
        }

        project.createTask<GeneratePagesTask>("librarianGeneratePages").apply {
            this.extension = extension
        }
    }
}