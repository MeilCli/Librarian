package net.meilcli.librarian.plugin.internal

import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.LibraryGroup
import org.gradle.api.Project
import java.io.File

object GroupWriter {

    @UnstableDefault
    fun write(project: Project, extension: LibrarianExtension, groups: Collection<LibraryGroup>) {
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