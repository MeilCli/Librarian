package net.meilcli.librarian.plugin.internal

import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.License
import net.meilcli.librarian.plugin.entities.PomProject

object PomProjectTranslator {

    fun translate(pomProject: PomProject): Library {
        val name = pomProject.name ?: PlaceHolder.name
        val developer = pomProject.developers
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
        val author = if (developer.isNullOrEmpty()) PlaceHolder.author else developer
        val url = pomProject.url ?: PlaceHolder.url
        val licenses = pomProject.licenses
            ?.map { License(it.name ?: PlaceHolder.licenseName, it.url ?: PlaceHolder.licenseUrl) }
            ?: listOf(License(PlaceHolder.licenseName, PlaceHolder.licenseUrl))

        return Library(
            artifact = "${pomProject.group}:${pomProject.artifact}",
            name = name,
            author = author,
            url = url,
            description = pomProject.description,
            licenses = licenses
        )
    }
}