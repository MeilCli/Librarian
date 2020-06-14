package net.meilcli.librarian.plugin.internal.pomprojects

import net.meilcli.librarian.plugin.entities.IPomProject
import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.internal.IAggregator1
import net.meilcli.librarian.plugin.internal.ITranslator

class PomProjectsToLibrariesAggregator(
    private val libraryTranslator: ITranslator<IPomProject, Library>
) : IAggregator1<List<IPomProject>, List<Library>> {

    override fun aggregate(source: List<IPomProject>): List<Library> {
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