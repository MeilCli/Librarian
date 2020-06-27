package net.meilcli.librarian.plugin.tasks

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.ConfigurationArtifacts
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.internal.LibrarianException
import net.meilcli.librarian.plugin.internal.artifacts.*
import net.meilcli.librarian.plugin.internal.librarygroups.LocalLibraryGroupsWriter
import net.meilcli.librarian.plugin.presets.PresetGroups
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GeneratePresetGroupsTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @TaskAction
    fun action() {
        val extension = extension ?: return
        if (extension.pages.isEmpty()) {
            return
        }

        val dependencyGraphLoader = DependencyGraphLoader(project, extension.depthType, extension.ignoreArtifacts)
        val dependencyGraph = dependencyGraphLoader.load()
        val dependencyGraphValidator = DependencyGraphValidator(project, extension)

        if (dependencyGraphValidator.valid(dependencyGraph).not()) {
            throw LibrarianException("Librarian encounter too many configurations. please filter page.configurations")
        }

        val dependencyGraphToConfigurationArtifactsTranslator = DependencyGraphToConfigurationArtifactsTranslator(project, extension)
        val configurationArtifacts = dependencyGraphToConfigurationArtifactsTranslator.translate(dependencyGraph)

        for (page in extension.pages) {
            try {
                writePresetGroups(configurationArtifacts, page)
            } catch (exception: Exception) {
                project.logger.error("Failed Librarian, page: ${page.name}", exception)
            }
        }
    }

    private fun writePresetGroups(configurationArtifacts: List<ConfigurationArtifacts>, page: LibrarianPageExtension) {
        val pageFilter = ConfigurationArtifactsByPageFilter(page)
        val artifactsTranslator = ConfigurationArtifactsToArtifactsTranslator()
        val foundPresetGroups = mutableSetOf<LibraryGroup>()

        for (artifact in configurationArtifacts.let { pageFilter.filter(it) }.let { artifactsTranslator.translate(it) }) {
            val foundPresetGroup = PresetGroups.groups.find { it.artifacts.contains(artifact.artifact) }
            if (foundPresetGroup != null) {
                foundPresetGroups += foundPresetGroup
            }
        }

        val libraryGroupsWriter = LocalLibraryGroupsWriter(project)
        libraryGroupsWriter.write(foundPresetGroups)
    }
}