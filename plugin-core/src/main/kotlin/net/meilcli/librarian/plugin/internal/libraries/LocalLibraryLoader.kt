package net.meilcli.librarian.plugin.internal.libraries

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.Library
import org.gradle.api.Project
import java.io.File

class LocalLibraryLoader(
    private val project: Project
) : ILibraryLoader {

    override fun loadLibraries(): List<Library> {
        val artifactsFolder = File(project.buildDir, "${LibrarianExtension.buildFolder}/${LibrarianExtension.artifactsFolder}")
        if (artifactsFolder.exists().not()) {
            return emptyList()
        }

        val result = mutableListOf<Library>()
        val json = Json(JsonConfiguration.Default)
        for (artifactFile in artifactsFolder.listFiles() ?: emptyArray()) {
            try {
                result += json.parse(Library.serializer(), artifactFile.readText())
            } catch (exception: Exception) {
                project.logger.warn("Librarian cannot read artifact file: ${artifactFile.absolutePath}", exception)
            }
        }

        return result
    }
}