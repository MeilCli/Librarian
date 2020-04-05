package net.meilcli.librarian.plugin.presets

import net.meilcli.librarian.plugin.entities.LibraryGroup

fun group(name: String, groupName: String, vararg artifactNames: String): LibraryGroup {
    return LibraryGroup(artifactNames.map { "$groupName:$it" }, name)
}

fun LibraryGroup.addAuthor(author: String): LibraryGroup {
    return LibraryGroup(
        artifacts = artifacts,
        name = name,
        author = author,
        description = description,
        url = url,
        licenses = licenses
    )
}