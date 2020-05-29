package net.meilcli.librarian.plugin.tasks

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsLoader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class ShowConfigurationsTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @TaskAction
    fun action() {
        val extension = extension ?: return

        val configurationArtifactsLoader = ConfigurationArtifactsLoader(project, extension)

        val result = configurationArtifactsLoader.load()
        project.logger.quiet("Configurations that have dependencies")
        for (entry in result) {
            project.logger.quiet("${entry.configurationNames.joinToString(" => ")} has ${entry.artifacts.size} entries")
        }
    }
}