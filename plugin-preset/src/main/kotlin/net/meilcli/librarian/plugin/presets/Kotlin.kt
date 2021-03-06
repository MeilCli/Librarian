@file:Suppress("SpellCheckingInspection")

package net.meilcli.librarian.plugin.presets

fun kotlin(){
    PresetGroups += group(
        "Kotlin",
        "org.jetbrains.kotlin",
        "kotlin-stdlib",
        "kotlin-stdlib-jdk8",
        "kotlin-stdlib-jdk7",
        "kotlin-stdlib-jre7",
        "kotlin-stdlib-jre8",
        "kotlin-stdlib-js",
        "kotlin-stdlib-common",
        "kotlin-reflect",
        "kotlin-test",
        "kotlin-test-junit",
        "kotlin-test-junit5",
        "kotlin-test-common",
        "kotlin-test-js",
        "kotlin-android-extensions-runtime",
        "kotlin-android-extensions",
        "kotlin-serialization",
        "kotlin-gradle-plugin",
        "kotlin-noarg",
        "kotlin-allopen"
    )
    PresetGroups += group(
        "kotlinx.serialization",
        "org.jetbrains.kotlinx",
        "kotlinx-serialization-runtime",
        "kotlinx-serialization-runtime-common",
        "kotlinx-serialization-runtime-js",
        "kotlinx-serialization-runtime-native",
        "kotlinx-serialization-runtime-iosx64",
        "kotlinx-serialization-runtime-macosx64",
        "kotlinx-serialization-runtime-iosarm64",
        "kotlinx-serialization-runtime-tvosarm64",
        "kotlinx-serialization-runtime-linuxx64",
        "kotlinx-serialization-runtime-watchosx86",
        "kotlinx-serialization-runtime-watchosarm64",
        "kotlinx-serialization-runtime-tvosx64",
        "kotlinx-serialization-runtime-mingwx64",
        "kotlinx-serialization-runtime-iosarm32",
        "kotlinx-serialization-runtime-watchosarm32",
        "kotlinx-serialization-runtime-jsonparser",
        "kotlinx-serialization-runtime-linuxarm32hfp",
        "kotlinx-serialization-runtime-mingwx86",
        "kotlinx-serialization-runtime-linuxarm64",
        "kotlinx-serialization-runtime-wasm32",
        "kotlinx-serialization-protobuf-common",
        "kotlinx-serialization-protobuf",
        "kotlinx-serialization-cbor"
    )
    PresetGroups += group(
        "kotlinx.coroutines",
        "org.jetbrains.kotlinx",
        "kotlinx-coroutines-core",
        "kotlinx-coroutines-android",
        "kotlinx-coroutines-jdk8",
        "kotlinx-coroutines-rx2",
        "kotlinx-coroutines-reactor",
        "kotlinx-coroutines-javafx",
        "kotlinx-coroutines-reactive",
        "kotlinx-coroutines-core-common",
        "kotlinx-coroutines-core-native",
        "kotlinx-coroutines-core-js",
        "kotlinx-coroutines-core-iosx64",
        "kotlinx-coroutines-core-iosarm64",
        "kotlinx-coroutines-core-macosx64",
        "kotlinx-coroutines-core-linuxx64",
        "kotlinx-coroutines-core-iosarm32",
        "kotlinx-coroutines-core-windowsx64",
        "kotlinx-coroutines-core-mingwx64",
        "kotlinx-coroutines-core-tvosarm64",
        "kotlinx-coroutines-core-watchosx86",
        "kotlinx-coroutines-core-watchosarm64",
        "kotlinx-coroutines-core-tvosx64",
        "kotlinx-coroutines-core-watchosarm32",
        "kotlinx-coroutines-test"
    )
}