package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactByPageFilter
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactLoader
import net.meilcli.librarian.plugin.internal.librarygroups.LocalLibraryGroupWriter
import net.meilcli.librarian.plugin.presets.PresetGroups
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GeneratePresetGroupsTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @UnstableDefault
    @TaskAction
    fun action() {
        val extension = extension ?: return
        if (extension.pages.isEmpty()) {
            return
        }

        val configurationArtifactLoader = ConfigurationArtifactLoader(project, extension)
        val configurationArtifacts = configurationArtifactLoader.load()

        for (page in extension.pages) {
            try {
                loadDependency(configurationArtifacts, page)
            } catch (exception: Exception) {
                project.logger.error("Failed Librarian, page: ${page.name}", exception)
            }
        }
    }

    @UnstableDefault
    private fun loadDependency(configurationArtifacts: List<ConfigurationArtifact>, page: LibrarianPageExtension) {
        val pageFilter = ConfigurationArtifactByPageFilter(page)
        val foundPresetGroups = mutableSetOf<LibraryGroup>()

        for (artifact in configurationArtifacts.let { pageFilter.filter(it) }.flatMap { it.artifacts }.distinct()) {
            val foundPresetGroup = PresetGroups.groups.find { it.artifacts.contains(artifact.artifact) }
            if (foundPresetGroup != null) {
                foundPresetGroups += foundPresetGroup
            }
        }

        val libraryGroupWriter = LocalLibraryGroupWriter(project)
        libraryGroupWriter.write(foundPresetGroups)
    }
}