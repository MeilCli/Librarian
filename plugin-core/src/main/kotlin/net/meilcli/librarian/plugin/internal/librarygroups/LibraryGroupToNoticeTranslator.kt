package net.meilcli.librarian.plugin.internal.librarygroups

import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.ITranslator
import net.meilcli.librarian.plugin.internal.Placeholder

class LibraryGroupToNoticeTranslator : ITranslator<LibraryGroup, Notice> {

    override fun translate(source: LibraryGroup): Notice {
        return Notice(
            artifacts = source.artifacts,
            name = source.name,
            author = source.author ?: Placeholder.author,
            url = source.url ?: Placeholder.url,
            description = source.description,
            licenses = source.licenses ?: emptyList()
        )
    }
}