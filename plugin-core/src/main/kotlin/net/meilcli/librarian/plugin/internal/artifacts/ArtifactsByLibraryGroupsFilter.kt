package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.internal.IFilter

class ArtifactsByLibraryGroupsFilter(
    private val libraryGroups: List<LibraryGroup>
) : IFilter<Collection<Artifact>> {

    override fun filter(source: Collection<Artifact>): Collection<Artifact> {
        return source.filter { x -> libraryGroups.all { y -> y.artifacts.contains(x.artifact).not() } }
    }
}