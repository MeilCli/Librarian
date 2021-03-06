package net.meilcli.librarian.plugin.internal.notices

import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.entities.NoticeResource
import net.meilcli.librarian.plugin.internal.IOverride
import net.meilcli.librarian.plugin.internal.Placeholder

class NoticeOverride : IOverride<Notice> {

    override fun override(source: Notice, override: Notice): Notice {
        val name = if (override.name != Placeholder.name) override.name else source.name
        val author = if (override.author != Placeholder.author) override.author else source.author
        val url = if (override.url != Placeholder.url) override.url else source.url
        val description = override.description
        val resources = if (override.resources.isNotEmpty()) {
            when {
                override.resources.all { it.licenses.isEmpty() } -> {
                    source.resources
                }
                source.resources.size == override.resources.size -> {
                    source.resources.zip(override.resources).map { NoticeResource(it.first.artifacts, it.second.licenses) }
                }
                else -> override.resources
            }
        } else {
            source.resources
        }
        return Notice(
            name = name,
            author = author,
            url = url,
            description = description,
            resources = resources
        )
    }
}