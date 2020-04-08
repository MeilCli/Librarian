package net.meilcli.librarian.plugin.internal.libraries

import kotlinx.serialization.json.Json
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.internal.IWriter
import org.gradle.api.Project
import java.io.File

class LocalLibraryWriter(
    private val project: Project
) : IWriter<List<Library>> {

    override fun write(source: List<Library>) {
        val outputDirectory = File(project.buildDir, "${LibrarianExtension.buildFolder}/${LibrarianExtension.artifactsFolder}")
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val json = Json {
            this.prettyPrint = true
        }

        for (entity in source) {
            val outputFile = File(outputDirectory, "${entity.artifact.replace(':', '-')}.json")
            val text = json.stringify(Library.serializer(), entity)
            outputFile.writeText(text, Charsets.UTF_8)
        }
    }
}