package net.meilcli.librarian.plugin.presets

fun koin() {
    PresetGroups += group(
        "Koin",
        "org.koin",
        "koin-android",
        "koin-core",
        "koin-androidx-viewmodel",
        "koin-core-ext",
        "koin-test",
        "koin-androidx-scope",
        "koin-reflect",
        "koin-android-viewmodel",
        "koin-androidx-ext",
        "koin-android-scope",
        "koin-logger-slf4j",
        "koin-java",
        "koin-androidx-fragment",
        "koin-spark",
        "koin-ktor",
        "koin-gradle-plugin",
        "koin-android-ext",
        "koin-android-architecture"
    )
        .addAuthor("insert-Koin.io")
        .addUrl("https://github.com/InsertKoinIO/koin")
        .addLicense("Apache License 2.0", "https://github.com/InsertKoinIO/koin/blob/master/LICENSE")
}