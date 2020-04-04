package net.meilcli.librarian.plugin.presets

fun librarian() {
    PresetGroups += group(
        "Librarian",
        "net.meilcli.librarian",
        "plugin-core",
        "plugin-preset",
        "ui-core",
        "ui-activity",
        "ui-fragment",
        "ui-serializer-kotlin"
    )
}