package net.meilcli.librarian.plugin.presets

fun facebook() {
    PresetGroups += group(
        "Bolts",
        "com.parse.bolts",
        "bolts-tasks",
        "bolts-android",
        "bolts-applinks"
    ).addAuthor("Facebook, Inc. and its affiliates.")
}