package net.meilcli.librarian.plugin.internal.libraries

import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.License
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.entities.NoticeResource
import net.meilcli.librarian.plugin.internal.ITranslator
import net.meilcli.librarian.plugin.internal.Placeholder

class LibrariesToNoticeTranslator : ITranslator<List<Library>, Notice> {

    override fun translate(source: List<Library>): Notice {
        return Notice(
            name = source.map { it.name }.firstOrNull() ?: Placeholder.name,
            author = source.map { it.author }.firstOrNull() ?: Placeholder.author,
            url = source.map { it.url }.firstOrNull() ?: Placeholder.url,
            description = source.map { it.description }.firstOrNull(),
            resources = listOf(
                NoticeResource(
                    artifacts = source.map { it.artifact }.sortedBy { it },
                    licenses = source.map { it.licenses }.firstOrNull() ?: listOf(License(Placeholder.licenseName, Placeholder.licenseUrl))
                )
            )
        )
    }
}