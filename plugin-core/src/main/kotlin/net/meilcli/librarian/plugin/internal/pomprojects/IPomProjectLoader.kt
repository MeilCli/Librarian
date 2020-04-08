package net.meilcli.librarian.plugin.internal.pomprojects

import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.PomProject

interface IPomProjectLoader {

    fun load(artifact: Artifact): PomProject?
}