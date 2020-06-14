package net.meilcli.librarian.plugin.presets

import net.meilcli.librarian.plugin.entities.LibraryGroup

fun java() {
    PresetGroups += LibraryGroup(
        artifacts = listOf("javax.inject:javax.inject"),
        name = "javax.inject"
    ).addAuthor("The JSR-330 Expert Group")
}