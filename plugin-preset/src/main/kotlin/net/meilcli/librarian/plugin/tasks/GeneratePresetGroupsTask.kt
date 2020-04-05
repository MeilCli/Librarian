package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.internal.ArtifactLoader
import net.meilcli.librarian.plugin.internal.GroupWriter
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

        val artifactLoaderResult = ArtifactLoader().load(project, extension.depthType)

        for (page in extension.pages) {
            try {
                loadDependency(artifactLoaderResult, page)
            } catch (exception: Exception) {
                project.logger.error("Failed Librarian, page: ${page.name}", exception)
            }
        }
    }

    @UnstableDefault
    private fun loadDependency(artifactLoaderResult: ArtifactLoader.Result, page: LibrarianPageExtension) {
        val queue = mutableSetOf<Artifact>()

        for (configuration in page.configurations) {
            val artifactResult = artifactLoaderResult.entries.firstOrNull { it.configurationName == configuration }
            if (artifactResult == null) {
                project.logger.warn("Librarian cannot resolve unknown configuration: $configuration")
                continue
            }
            queue.addAll(artifactResult.artifacts)
        }

        val foundPresetGroups = mutableSetOf<LibraryGroup>()
        for (artifact in queue) {
            val foundPresetGroup = PresetGroups.groups.find { it.artifacts.contains(artifact.artifact) }
            if (foundPresetGroup != null) {
                foundPresetGroups += foundPresetGroup
            }
        }

        GroupWriter.write(project, foundPresetGroups)
    }
}