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
            "com.google.android.gms:play-services-ads",
            "com.google.android.gms:play-services-ads-base",
            "com.google.android.gms:play-services-ads-identifier",
            "com.google.android.gms:play-services-ads-lite",
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
            "com.google.android.gms:play-services-clearcut",
            "com.google.android.gms:play-services-drive",
            "com.google.android.gms:play-services-fitness",
            "com.google.android.gms:play-services-flags",
            "com.google.android.gms:play-services-games",
            "com.google.android.gms:play-services-gass",
            "com.google.android.gms:play-services-gcm",
            "com.google.android.gms:play-services-identity",
            "com.google.android.gms:play-services-iid",
            "com.google.android.gms:play-services-location",
            "com.google.android.gms:play-services-maps",
            "com.google.android.gms:play-services-measurement-base",
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
}