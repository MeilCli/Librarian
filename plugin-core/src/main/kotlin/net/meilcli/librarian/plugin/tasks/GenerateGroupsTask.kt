package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.entities.License
import net.meilcli.librarian.plugin.internal.Placeholder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GenerateGroupsTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @UnstableDefault
    @TaskAction
    fun action() {
        val extension = extension ?: return
        val groups = extension.groups.map {
            val licenses = if (it.licenseName != null || it.licenseUrl != null) {
                listOf(
                    License(
                        it.licenseName ?: Placeholder.licenseName,
                        it.licenseUrl ?: Placeholder.licenseUrl
                    )
                )
            } else {
                null
            }
            LibraryGroup(
                artifacts = it.artifacts.toList(),
                name = it.name,
                author = it.author,
                url = it.url,
                description = it.description,
                licenses = licenses
            )
        }

        val outputDirectory = File(project.rootProject.rootDir, "${extension.dataFolderName}/${extension.groupsFolderName}")
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val json = Json {
            this.prettyPrint = true
        }

        for (group in groups) {
            val outputFile = File(outputDirectory, "${group.name}.json")
            val text = json.stringify(LibraryGroup.serializer(), group)
            outputFile.writeText(text, Charsets.UTF_8)
        }
    }
}