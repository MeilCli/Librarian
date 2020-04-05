package net.meilcli.librarian.plugin.presets

import net.meilcli.librarian.plugin.entities.LibraryGroup

fun bintray() {
    PresetGroups += LibraryGroup(
        artifacts = listOf("com.jfrog.bintray.gradle:gradle-bintray-plugin"),
        name = "gradle-bintray-plugin",
        url = "https://github.com/bintray/gradle-bintray-plugin",
        author = "JFrog",
        description = null,
        licenses = null
    )
}