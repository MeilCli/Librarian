package net.meilcli.librarian.plugin.presets

import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.entities.License

fun gmazzo() {
    PresetGroups += LibraryGroup(
        artifacts = listOf("com.github.gmazzo:gradle-buildconfig-plugin"),
        name = "gradle-buildconfig-plugin",
        author = "Guillermo Mazzola",
        url = "https://github.com/gmazzo/gradle-buildconfig-plugin",
        description = null,
        licenses = listOf(
            License(
                name = "MIT License",
                url = "https://github.com/gmazzo/gradle-buildconfig-plugin/blob/master/LICENSE"
            )
        )
    )
}