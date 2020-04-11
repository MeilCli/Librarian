package net.meilcli.librarian.plugin.internal.libraries

import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.ITranslator

class LibraryToNoticeTranslator : ITranslator<Library, Notice> {

    override fun translate(source: Library): Notice {
        return Notice(
            artifacts = listOf(source.artifact),
            name = source.name,
            author = source.author,
            url = source.url,
            description = source.description,
            licenses = source.licenses
        )
    }
}