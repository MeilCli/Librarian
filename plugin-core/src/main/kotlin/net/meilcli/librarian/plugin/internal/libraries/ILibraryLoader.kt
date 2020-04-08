package net.meilcli.librarian.plugin.internal.libraries

import net.meilcli.librarian.plugin.entities.Library
import org.gradle.api.Project

interface ILibraryLoader {

    fun loadLibraries(project: Project): List<Library>
}