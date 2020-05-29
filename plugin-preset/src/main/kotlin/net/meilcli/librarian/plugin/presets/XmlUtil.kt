package net.meilcli.librarian.plugin.presets

import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.entities.License

fun xmlUtil() {
    PresetGroups += LibraryGroup(
        artifacts = listOf(
            "net.devrieze:xmlutil",
            "net.devrieze:xmlutil-jvm",
            "net.devrieze:xmlutil-android",
            "net.devrieze:xmlutil-js",
            "net.devrieze:xmlutil-serialization",
            "net.devrieze:xmlutil-serialization-jvm",
            "net.devrieze:xmlutil-serialization-android",
            "net.devrieze:xmlutil-serialization-js"
        ),
        name = "XmlUtil",
        author = "pdvrieze",
        url = "https://github.com/pdvrieze/xmlutil",
        description = null,
        licenses = listOf(
            License("Apache License 2.0", "https://github.com/pdvrieze/xmlutil/blob/master/COPYING")
        )
    )
}