package net.meilcli.librarian.plugin.internal.libraries

import net.meilcli.librarian.plugin.entities.Library

interface ILibraryLoader {

    fun loadLibraries(): List<Library>
}