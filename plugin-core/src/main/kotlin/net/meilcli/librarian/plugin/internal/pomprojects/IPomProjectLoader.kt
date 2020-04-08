package net.meilcli.librarian.plugin.internal.pomprojects

import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.PomProject
import org.gradle.api.Project

interface IPomProjectLoader {

    fun load(project: Project, artifact: Artifact): PomProject?
}