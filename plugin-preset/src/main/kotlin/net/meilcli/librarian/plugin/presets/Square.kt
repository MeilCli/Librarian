@file:Suppress("SpellCheckingInspection")

package net.meilcli.librarian.plugin.presets

fun square() {
    PresetGroups += group(
        "Okio",
        "com.squareup.okio",
        "okio-parent",
        "okio",
        "okio-js",
        "okio-metadata",
        "okio-multiplatform",
        "okio-iosx64",
        "okio-macosx64",
        "okio-iosarm64",
        "okio-linuxx64"
    ).addAuthor("Square, Inc.")
    PresetGroups += group(
        "OkHttp",
        "com.squareup.okhttp3",
        "parent",
        "okhttp",
        "logging-interceptor",
        "mockwebserver",
        "okhttp-urlconnection",
        "okhttp-testing-support",
        "okhttp-tls",
        "okhttp-sse",
        "okhttp-dnsoverhttps",
        "okcurl",
        "okhttp-bom",
        "okhttp-brotli"
    ).addAuthor("Square, Inc.")
    PresetGroups += group(
        "Retrofit",
        "com.squareup.retrofit2",
        "parent",
        "retrofit",
        "retrofit-mock",
        "retrofit-converters",
        "retrofit-adapters",
        "converter-jackson",
        "converter-scalars",
        "converter-simplexml",
        "converter-jaxb",
        "converter-java8",
        "converter-guava",
        "converter-gson",
        "converter-moshi",
        "converter-wire",
        "converter-protobuf",
        "adapter-rxjava",
        "adapter-rxjava2",
        "adapter-scala",
        "adapter-java8",
        "adapter-guava"
    ).addAuthor("Square, Inc.")
    PresetGroups += group(
        "Picasso",
        "com.squareup.picasso",
        "picasso-parent",
        "picasso",
        "picasso-pollexor"
    )
    PresetGroups += group(
        "Moshi",
        "com.squareup.moshi",
        "moshi-parent",
        "moshi",
        "moshi-kotlin",
        "moshi-adapters",
        "moshi-kotlin-codegen"
    ).addAuthor("Square, Inc.")
    PresetGroups += group(
        "LeakCanary",
        "com.squareup.leakcanary",
        "leakcanary-android",
        "leakcanary-android-no-op",
        "leakcanary-android-core",
        "leakcanary-android-process",
        "leakcanary-android-instrumentation",
        "leakcanary-object-watcher",
        "leakcanary-deobfuscation-gradle-plugin",
        "leakcanary-object-watcher-android-support-fragments",
        "leakcanary-object-watcher-android",
        "leakcanary-object-watcher-android-androidx",
        "shark-log",
        "shark-graph",
        "shark-android",
        "shark",
        "shark-cli",
        "shark-hprof"
    )
}
