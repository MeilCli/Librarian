package net.meilcli.librarian.plugin.presets

fun realm() {
    PresetGroups += group(
        "Realm",
        "io.realm",
        "realm-android-library",
        "realm-annotations",
        "realm-android",
        "realm-android-kotlin-extensions",
        "android-adapters",
        "realm-transformer",
        "realm-gradle-plugin",
        "realm-android-library-object-server",
        "realm-android-kotlin-extensions-object-server"
    )
        .addAuthor("Realm Inc.")
        .addUrl("http://realm.io")
        .addLicense("The Apache Software License, Version 2.0", "http://www.apache.org/licenses/LICENSE-2.0.txt")
}