package net.meilcli.librarian.plugin.presets

fun coil() {
    PresetGroups += group(
        "Coil",
        "io.coil-kt",
        "coil",
        "coil-base",
        "coil-gif",
        "coil-svg",
        "coil-video"
    )
}