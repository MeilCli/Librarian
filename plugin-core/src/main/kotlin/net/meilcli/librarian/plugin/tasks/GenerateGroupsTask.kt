package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.internal.LibrarianGroupsToLibraryGroupsTranslator
import net.meilcli.librarian.plugin.internal.librarygroups.LocalLibraryGroupsWriter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GenerateGroupsTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @UnstableDefault
    @TaskAction
    fun action() {
        val extension = extension ?: return

        val libraryGroupsTranslator = LibrarianGroupsToLibraryGroupsTranslator()
        val libraryGroupsWriter = LocalLibraryGroupsWriter(project)

        libraryGroupsWriter.write(libraryGroupsTranslator.translate(extension.groups))
    }
}