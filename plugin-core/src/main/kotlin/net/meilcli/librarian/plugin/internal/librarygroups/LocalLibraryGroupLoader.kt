package net.meilcli.librarian.plugin.internal.librarygroups

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.LibraryGroup
import org.gradle.api.Project
import java.io.File

class LocalLibraryGroupLoader : ILibraryGroupLoader {

    override fun loadLibraryGroups(project: Project): List<LibraryGroup> {
        val groupsFolder = File(project.buildDir, "${LibrarianExtension.buildFolder}/${LibrarianExtension.groupsFolder}")
        if (groupsFolder.exists().not()) {
            return emptyList()
        }

        val result = mutableListOf<LibraryGroup>()
        val json = Json(JsonConfiguration.Default)
        for (groupFile in groupsFolder.listFiles() ?: emptyArray()) {
            try {
                result += json.parse(LibraryGroup.serializer(), groupFile.readText())
            } catch (exception: Exception) {
                project.logger.warn("Librarian cannot read group file: ${groupFile.absolutePath}", exception)
            }
        }

        return result
    }
}