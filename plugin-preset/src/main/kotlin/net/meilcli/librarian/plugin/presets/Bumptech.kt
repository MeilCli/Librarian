package net.meilcli.librarian.plugin.presets

fun bumptech() {
    PresetGroups += group(
        "Glide",
        "com.github.bumptech.glide",
        "glide",
        "annotations",
        "gifdecoder",
        "disklrucache",
        "okhttp3-integration",
        "okhttp-integration",
        "compiler",
        "volley-integration",
        "recyclerview-integration",
        "okhttp3-ssl-integration",
        "mocks",
        "concurrent-integration",
        "gifencoder-integration"
    )
}