package net.meilcli.librarian.plugin.presets

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
}