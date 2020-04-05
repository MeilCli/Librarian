package net.meilcli.librarian.plugin.presets

import net.meilcli.librarian.plugin.entities.LibraryGroup

fun junit() {
    PresetGroups += LibraryGroup(
        artifacts = listOf("junit:junit"),
        name = "JUnit",
        author = "JUnit Team",
        description = null,
        url = null,
        licenses = null
    )
    PresetGroups += LibraryGroup(
        artifacts = listOf(
            "org.junit.jupiter:junit-jupiter-api",
            "org.junit.jupiter:junit-jupiter-engine",
            "org.junit.jupiter:junit-jupiter-params",
            "org.junit.jupiter:junit-jupiter"
        ),
        name = "JUnit",
        author = "JUnit Team",
        description = null,
        url = null,
        licenses = null
    )
}