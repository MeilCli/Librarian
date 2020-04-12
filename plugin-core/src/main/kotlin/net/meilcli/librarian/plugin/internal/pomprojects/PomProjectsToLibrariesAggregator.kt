package net.meilcli.librarian.plugin.internal.pomprojects

import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.PomProject
import net.meilcli.librarian.plugin.internal.IAggregator1
import net.meilcli.librarian.plugin.internal.ITranslator

class PomProjectsToLibrariesAggregator(
    private val libraryTranslator: ITranslator<PomProject, Library>
) : IAggregator1<List<PomProject>, List<Library>> {

    override fun aggregate(source: List<PomProject>): List<Library> {
        return source.asSequence()
            .map { libraryTranslator.translate(it) }
            .groupBy { it.artifact }
            .map {
                val library = it.value.first() // ToDo: Priority
                Library(
                    artifact = library.artifact,
                    name = library.name,
                    author = library.author,
                    url = library.url,
                    description = library.description,
                    licenses = it.value.flatMap { x -> x.licenses }.distinct()
                )
            }
    }
}