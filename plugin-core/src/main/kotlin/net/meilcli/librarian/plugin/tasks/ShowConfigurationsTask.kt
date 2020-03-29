package net.meilcli.librarian.plugin.tasks

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.internal.ArtifactLoader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class ShowConfigurationsTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @TaskAction
    fun action() {
        val extension = extension ?: return
        val result = ArtifactLoader().load(project, extension.depthType)
        project.logger.quiet("Configurations that have dependencies")
        for (entry in result.entries) {
            project.logger.quiet("${entry.configurationName} has ${entry.artifacts.size} entries")
        }
    }
}