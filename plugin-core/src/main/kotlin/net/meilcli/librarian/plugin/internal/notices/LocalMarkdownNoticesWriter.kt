package net.meilcli.librarian.plugin.internal.notices

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.IWriter
import org.gradle.api.Project
import java.io.File

class LocalMarkdownNoticesWriter(
    private val project: Project,
    private val extension: LibrarianExtension,
    private val page: LibrarianPageExtension
) : IWriter<List<Notice>> {

    override fun write(source: List<Notice>) {
        val sb = StringBuilder()
        sb.appendln("# ${page.title}")
        sb.appendln("*This markdown is auto generated by [Librarian](https://github.com/MeilCli/Librarian)*")
        sb.appendln()
        page.description?.also {
            sb.appendln(it)
            sb.appendln()
        }
        sb.appendln("## Using")
        for (notice in source) {
            sb.appendln("- [${notice.name}](${notice.url}), licensed on ${notice.licenses.joinToString { "[${it.name}](${it.url})" }}, made by ${notice.author}")
        }

        val outputDirectory = File(project.rootProject.rootDir, "${extension.dataFolderName}/${page.name}")
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val outputFile = File(outputDirectory, page.markdownFileName)
        outputFile.writeText(sb.toString(), Charsets.UTF_8)
    }
}