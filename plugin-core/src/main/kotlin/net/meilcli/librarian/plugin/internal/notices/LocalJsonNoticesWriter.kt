package net.meilcli.librarian.plugin.internal.notices

import kotlinx.serialization.json.Json
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.entities.Notices
import net.meilcli.librarian.plugin.internal.IWriter
import org.gradle.api.Project
import java.io.File

class LocalJsonNoticesWriter(
    private val project: Project,
    private val extension: LibrarianExtension,
    private val page: LibrarianPageExtension
) : IWriter<List<Notice>> {

    override fun write(source: List<Notice>) {
        val outputDirectory = File(project.rootProject.rootDir, "${extension.dataFolderName}/${page.name}")
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val json = Json {
            this.prettyPrint = true
        }

        val outputFile = File(outputDirectory, page.jsonFileName)
        val text = json.stringify(Notices.serializer(), Notices(page.title, page.description, source))
        outputFile.writeText(text, Charsets.UTF_8)

        page.jsonAdditionalOutputPath?.also {
            if (it.parentFile.exists().not()) {
                it.parentFile.mkdirs()
            }
            it.writeText(text, Charsets.UTF_8)
        }
    }
}