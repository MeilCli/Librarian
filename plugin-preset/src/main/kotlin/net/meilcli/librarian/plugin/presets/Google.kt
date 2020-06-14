package net.meilcli.librarian.plugin.presets

import net.meilcli.librarian.plugin.entities.LibraryGroup

fun google() {
    PresetGroups += group(
        "Google Mobile Ads SDK - Android Mediation",
        "com.google.ads.mediation",
        "adcolony",
        "applovin",
        "chartboost",
        "facebook",
        "fyber",
        "imobile",
        "inmobi",
        "ironsource",
        "maio",
        "mobfox",
        "mopub",
        "mytarget",
        "nend",
        "tapjoy",
        "unity",
        "verizonmedia",
        "vpon",
        "vungle",
        "yandex"
    )
        .addAuthor("Google Inc.")
        .addUrl("https://github.com/googleads/googleads-mobile-android-mediation")
        .addLicense(
            "Apache License 2.0",
            "https://github.com/googleads/googleads-mobile-android-mediation/blob/master/LICENSE"
        )

    PresetGroups += LibraryGroup(
        listOf(
            "com.google.android.gms:play-services",
            "com.google.android.gms:play-services-ads",
            "com.google.android.gms:play-services-ads-base",
            "com.google.android.gms:play-services-ads-identifier",
            "com.google.android.gms:play-services-ads-lite",
            "com.google.android.gms:play-services-appinvite",
            "com.google.android.gms:play-services-analytics",
            "com.google.android.gms:play-services-analytics-impl",
            "com.google.android.gms:play-services-audience",
            "com.google.android.gms:play-services-auth",
            "com.google.android.gms:play-services-auth-api-phone",
            "com.google.android.gms:play-services-auth-base",
            "com.google.android.gms:play-services-awareness",
            "com.google.android.gms:play-services-base",
            "com.google.android.gms:play-services-basement",
            "com.google.android.gms:play-services-cast",
            "com.google.android.gms:play-services-cast-framework",
            "com.google.android.gms:play-services-clearcut",
            "com.google.android.gms:play-services-drive",
            "com.google.android.gms:play-services-fitness",
            "com.google.android.gms:play-services-fido",
            "com.google.android.gms:play-services-flags",
            "com.google.android.gms:play-services-games",
            "com.google.android.gms:play-services-gass",
            "com.google.android.gms:play-services-gcm",
            "com.google.android.gms:play-services-identity",
            "com.google.android.gms:play-services-iid",
            "com.google.android.gms:play-services-instantapps",
            "com.google.android.gms:play-services-location",
            "com.google.android.gms:play-services-maps",
            "com.google.android.gms:play-services-measurement",
            "com.google.android.gms:play-services-measurement-base",
            "com.google.android.gms:play-services-measurement-api",
            "com.google.android.gms:play-services-measurement-impl",
            "com.google.android.gms:play-services-measurement-sdk",
            "com.google.android.gms:play-services-measurement-sdk-api",
            "com.google.android.gms:play-services-nearby",
            "com.google.android.gms:play-services-panorama",
            "com.google.android.gms:play-services-phenotype",
            "com.google.android.gms:play-services-places",
            "com.google.android.gms:play-services-places-placereport",
            "com.google.android.gms:play-services-plus",
            "com.google.android.gms:play-services-safetynet",
            "com.google.android.gms:play-services-stats",
            "com.google.android.gms:play-services-tagmanager",
            "com.google.android.gms:play-services-tagmanager-api",
            "com.google.android.gms:play-services-tagmanager-v4-impl",
            "com.google.android.gms:play-services-tasks",
            "com.google.android.gms:play-services-vision",
            "com.google.android.gms:play-services-vision-common",
            "com.google.android.gms:play-services-wallet",
            "com.google.android.gms:play-services-wearable"
        ),
        "Google Play Services"
    )
        .addAuthor("Google Inc.")
        .addUrl("https://developers.google.com/android/guides/overview")

    PresetGroups += group(
        "ExoPlayer",
        "com.google.android.exoplayer",
        "exoplayer",
        "exoplayer-core",
        "exoplayer-dash",
        "exoplayer-hls",
        "exoplayer-smoothstreaming",
        "exoplayer-testutils",
        "exoplayer-ui",
        "extension-cast",
        "extension-cronet",
        "extension-gvr",
        "extension-ima",
        "extension-jobdispatcher",
        "extension-leanback",
        "extension-mediasession",
        "extension-okhttp",
        "extension-rtmp",
        "extension-workmanager"
    )
        .addAuthor("Google Inc.")
        .addUrl("https://github.com/google/ExoPlayer")
        .addLicense(
            "Apache License 2.0",
            "https://github.com/google/ExoPlayer/blob/release-v2/LICENSE"
        )

    PresetGroups += group(
        "Dagger",
        "com.google.dagger",
        "dagger",
        "dagger-android",
        "dagger-android-support",
        "dagger-android-processor",
        "dagger-android-jarimpl",
        "dagger-android-support-jarimpl",
        "dagger-android-support-legacy",
        "dagger-android-legacy",
        "dagger-compiler",
        "dagger-gwt",
        "dagger-grpc-server",
        "dagger-grpc-server-annotations",
        "dagger-grpc-server-processor",
        "hilt-android",
        "hilt-android-testing",
        "hilt-android-gradle-plugin",
        "hilt-android-compiler",
        "dagger-spi",
        "dagger-producers",
        "dagger-parent",
        "dagger-lint",
        "dagger-lint-aar"
    )

    PresetGroups += group(
        "Firebase",
        "com.google.firebase",
        "firebase-analytics",
        "firebase-analytics-ktx",
        "firebase-analytics-impl",
        "firebase-abt",
        "firebase-appindexing",
        "firebase-auth",
        "firebase-auth-ktx",
        "firebase-auth-interop",
        "firebase-bom",
        "firebase-common",
        "firebase-common-ktx",
        "firebase-components",
        "firebase-crash",
        "firebase-crashlytics",
        "firebase-crashlytics-ndx",
        "firebase-config",
        "firebase-config-ktx",
        "firebase-database",
        "firebase-database-ktx",
        "firebase-database-collection",
        "firebase-database-connection",
        "firebase-datatransport",
        "firebase-dynamic-links",
        "firebase-dynamic-links-ktx",
        "firebase-encoders-json",
        "firebase-firestore",
        "firebase-firestore-ktx",
        "firebase-functions",
        "firebase-functions-ktx",
        "firebase-inappmessaging",
        "firebase-inappmessaging-ktx",
        "firebase-inappmessaging-display",
        "firebase-inappmessaging-display-ktx",
        "firebase-installations",
        "firebase-installations-interop",
        "firebase-iid",
        "firebase-iid-interop",
        "firebase-perf",
        "firebase-remote-config",
        "firebase-remote-config-ktx",
        "firebase-storage",
        "firebase-storage-ktx",
        "firebase-storage-common",
        "firebase-messaging",
        "firebase-measurement-connector",
        "firebase-ml-vision",
        "firebase-ml-vision-image-label-model",
        "firebase-ml-vision-object-detection-model",
        "firebase-ml-vision-face-model",
        "firebase-ml-vision-barcode-model",
        "firebase-ml-vision-automl",
        "firebase-ml-natural-language",
        "firebase-ml-natural-language-language-id-model",
        "firebase-ml-natural-language-translate-model",
        "firebase-ml-natural-language-smart-reply-model",
        "firebase-ml-model-interpreter"
    )
        .addAuthor("Google Inc.")
        .addUrl("https://firebase.google.com")
        .addArtifacts(
            listOf(
                "com.google.android.datatransport:transport-api",
                "com.google.android.datatransport:transport-backend-cct",
                "com.google.android.datatransport:transport-runtime"
            )
        )

    PresetGroups += LibraryGroup(
        listOf("com.google.code.gson:gson"),
        "Gson"
    ).addAuthor("Google Inc.")
}