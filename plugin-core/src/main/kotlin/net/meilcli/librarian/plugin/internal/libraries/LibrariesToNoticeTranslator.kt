package net.meilcli.librarian.plugin.internal.libraries

import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.License
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.ITranslator
import net.meilcli.librarian.plugin.internal.Placeholder

class LibrariesToNoticeTranslator : ITranslator<List<Library>, Notice> {

    override fun translate(source: List<Library>): Notice {
        return Notice(
            artifacts = source.map { it.artifact }.sortedBy { it },
            name = source.firstOrNull()?.name ?: Placeholder.name,
            author = source.firstOrNull()?.author ?: Placeholder.author,
            url = source.firstOrNull()?.url ?: Placeholder.url,
            description = source.firstOrNull()?.description,
            licenses = source.firstOrNull()?.licenses ?: listOf(License(Placeholder.licenseName, Placeholder.licenseUrl))
        )
    }
}