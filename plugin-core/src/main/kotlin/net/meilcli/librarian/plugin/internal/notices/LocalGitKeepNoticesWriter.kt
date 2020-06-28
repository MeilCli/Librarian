package net.meilcli.librarian.plugin.internal.notices

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.IWriter
import org.gradle.api.Project
import java.io.File

class LocalGitKeepNoticesWriter(
    private val project: Project,
    private val extension: LibrarianExtension
) : IWriter<List<Notice>> {

    companion object {

        private const val gitKeepFileName = ".gitkeep"
    }

    override fun write(source: List<Notice>) {
        val outputDirectory = File(project.rootProject.rootDir, extension.dataFolderName)
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val outputFile = File(outputDirectory, gitKeepFileName)
        outputFile.writeText("", Charsets.UTF_8)
    }
}