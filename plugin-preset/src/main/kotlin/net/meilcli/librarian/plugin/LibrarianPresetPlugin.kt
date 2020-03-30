package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.extensions.createTask
import net.meilcli.librarian.plugin.tasks.GeneratePresetGroupsTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibrarianPresetPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = checkNotNull(project.extensions.findByType(LibrarianExtension::class.java)) { "must apply plugin librarian" }

        project.createTask<GeneratePresetGroupsTask>("librarianGeneratePresetGroups").apply {
            this.extension = extension
        }
    }
}