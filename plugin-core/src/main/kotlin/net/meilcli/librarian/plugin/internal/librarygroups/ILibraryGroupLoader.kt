package net.meilcli.librarian.plugin.internal.librarygroups

import net.meilcli.librarian.plugin.entities.LibraryGroup

interface ILibraryGroupLoader {

    fun loadLibraryGroups(): List<LibraryGroup>
}