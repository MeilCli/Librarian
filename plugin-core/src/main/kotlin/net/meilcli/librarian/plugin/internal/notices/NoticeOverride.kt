package net.meilcli.librarian.plugin.internal.notices

import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.IOverride
import net.meilcli.librarian.plugin.internal.Placeholder

class NoticeOverride : IOverride<Notice> {

    override fun override(source: Notice, override: Notice): Notice {
        val artifacts = if (override.artifacts.isNotEmpty()) override.artifacts else source.artifacts
        val name = if (override.name != Placeholder.name) override.name else source.name
        val author = if (override.author != Placeholder.author) override.author else source.author
        val url = if (override.url != Placeholder.url) override.url else source.url
        val description = override.description ?: source.description
        val licenses = if (override.licenses.isNotEmpty()) override.licenses else source.licenses
        return Notice(
            artifacts = artifacts,
            name = name,
            author = author,
            url = url,
            description = description,
            licenses = licenses
        )
    }
}