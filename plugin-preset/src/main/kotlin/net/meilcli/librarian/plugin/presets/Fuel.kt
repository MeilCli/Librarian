package net.meilcli.librarian.plugin.presets

fun fuel() {
    PresetGroups += group(
        "Fuel",
        "com.github.kittinunf",
        "fuel",
        "fuel-gson",
        "fuel-android",
        "fuel-coroutines",
        "fuel-jackson",
        "fuel-moshi",
        "fuel-rxjava",
        "fuel-json",
        "fuel-forge",
        "fuel-livedata",
        "fuel-reactor",
        "fuel-kotlinx-serialization",
        "fuel-stetho",
        "fuel-test"
    ).addAuthor("Kittinun Vantasin")
}