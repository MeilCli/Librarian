package net.meilcli.librarian.plugin.internal

import net.meilcli.librarian.plugin.entities.LibraryGroup
import org.gradle.api.Project

interface ILibraryGroupLoader {

    fun loadLibraryGroups(project: Project): List<LibraryGroup>
}