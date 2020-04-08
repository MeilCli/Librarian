package net.meilcli.librarian.plugin.internal.librarygroups

import kotlinx.serialization.json.Json
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.internal.IWriter
import org.gradle.api.Project
import java.io.File

class LocalLibraryGroupWriter(
    private val project: Project
) : IWriter<Collection<LibraryGroup>> {

    override fun write(source: Collection<LibraryGroup>) {
        val outputDirectory = File(project.buildDir, "${LibrarianExtension.buildFolder}/${LibrarianExtension.groupsFolder}")
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val json = Json {
            this.prettyPrint = true
        }

        for (group in source) {
            val outputFile = File(outputDirectory, "${group.name}.json")
            val text = json.stringify(LibraryGroup.serializer(), group)
            outputFile.writeText(text, Charsets.UTF_8)
        }
    }
}