package net.meilcli.librarian.plugin.tasks

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.internal.LibrarianException
import net.meilcli.librarian.plugin.internal.artifacts.DependencyGraphLoader
import net.meilcli.librarian.plugin.internal.artifacts.DependencyGraphToConfigurationArtifactsTranslator
import net.meilcli.librarian.plugin.internal.artifacts.DependencyGraphValidator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class ShowFilteredDependencyGraphTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @TaskAction
    fun action() {
        val extension = extension ?: return

        val dependencyGraphLoader = DependencyGraphLoader(project, extension.depthType, extension.ignoreArtifacts)
        val dependencyGraph = dependencyGraphLoader.load()
        val dependencyGraphValidator = DependencyGraphValidator(extension)

        if (dependencyGraphValidator.valid(dependencyGraph).not()) {
            throw LibrarianException("Librarian encounter too many configurations. please filter page.configurations")
        }

        val dependencyGraphToConfigurationArtifactsTranslator = DependencyGraphToConfigurationArtifactsTranslator(project, extension)
        val configurationArtifacts = dependencyGraphToConfigurationArtifactsTranslator.translate(dependencyGraph)

        project.logger.quiet("DependencyGraph that filtered by page.configurations")
        for (entry in configurationArtifacts) {
            project.logger.quiet("${entry.configurationNames.joinToString(" => ") { it.name }} has ${entry.artifacts.size} entries")
        }
    }
}