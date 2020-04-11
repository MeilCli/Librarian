package net.meilcli.librarian.plugin.internal

import net.meilcli.librarian.plugin.LibrarianGroupExtension
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.entities.License

class LibrarianGroupsToLibraryGroupsTranslator : ITranslator<Collection<LibrarianGroupExtension>, List<LibraryGroup>> {

    override fun translate(source: Collection<LibrarianGroupExtension>): List<LibraryGroup> {
        return source.map {
            val licenses = if (it.licenseName != null || it.licenseUrl != null) {
                listOf(
                    License(
                        it.licenseName ?: Placeholder.licenseName,
                        it.licenseUrl ?: Placeholder.licenseUrl
                    )
                )
            } else {
                null
            }
            LibraryGroup(
                artifacts = it.artifacts.toList(),
                name = it.name,
                author = it.author,
                url = it.url,
                description = it.description,
                licenses = licenses
            )
        }
    }
}