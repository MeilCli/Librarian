package net.meilcli.librarian.plugin.internal.pomprojects

import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.License
import net.meilcli.librarian.plugin.entities.PomProject
import net.meilcli.librarian.plugin.internal.ITranslator
import net.meilcli.librarian.plugin.internal.Placeholder

class PomProjectToLibraryTranslator : ITranslator<PomProject, Library> {

    override fun translate(source: PomProject): Library {
        val name = source.name ?: Placeholder.name
        val developer = source.developers
            ?.filter { it.name != null || it.organization != null }
            ?.joinToString {
                if (it.organization != null && it.name != null) {
                    "${it.organization} - ${it.name}"
                } else if (it.organization != null) {
                    "${it.organization}"
                } else {
                    "${it.name}"
                }
            }
        val author = if (developer.isNullOrEmpty()) Placeholder.author else developer
        val url = source.url ?: Placeholder.url
        val licenses = source.licenses
            ?.map { License(it.name ?: Placeholder.licenseName, it.url ?: Placeholder.licenseUrl) }
            ?: listOf(License(Placeholder.licenseName, Placeholder.licenseUrl))

        return Library(
            artifact = "${source.group}:${source.artifact}",
            name = name,
            author = author,
            url = url,
            description = source.description,
            licenses = licenses
        )
    }
}