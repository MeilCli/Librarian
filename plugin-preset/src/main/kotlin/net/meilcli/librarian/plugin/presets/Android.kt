@file:Suppress("SpellCheckingInspection")

package net.meilcli.librarian.plugin.presets

import net.meilcli.librarian.plugin.entities.LibraryGroup

fun android() {
    PresetGroups += LibraryGroup(
        listOf(
            "androidx.activity:activity",
            "androidx.activity:activity-ktx",
            "androidx.annotation:annotation",
            "androidx.annotation:annotation-experimental",
            "androidx.annotation:annotation-experimental-lint",
            "androidx.appcompat:appcompat",
            "androidx.appcompat:appcompat-resources",
            "androidx.arch.core:core-common",
            "androidx.arch.core:core-runtime",
            "androidx.arch.core:core-testing",
            "androidx.asynclayoutinflater:asynclayoutinflater",
            "androidx.autofill:autofill",
            "androidx.benchmark:benchmark",
            "androidx.biometric:biometric",
            "androidx.browser:browser",
            "androidx.camera:camera-core",
            "androidx.camera:camera-camera2",
            "androidx.camera:camera-lifecycle",
            "androidx.camera:camera-view",
            "androidx.camera:camera-extensions",
            "androidx.car:car",
            "androidx.car:car-cluster",
            "androidx.cardview:cardview",
            "androidx.collection:collection",
            "androidx.collection:collection-ktx",
            "androidx.compose:compose-runtime",
            "androidx.compose:compose-compiler",
            "androidx.concurrent:concurrent-futures",
            "androidx.concurrent:concurrent-listenablefuture",
            "androidx.concurrent:concurrent-listenablefuture-callback",
            "androidx.concurrent:concurrent-futures-ktx",
            "androidx.coordinatorlayout:coordinatorlayout",
            "androidx.core:core",
            "androidx.core:core-ktx",
            "androidx.core:core-role",
            "androidx.cursoradapter:cursoradapter",
            "androidx.customview:customview",
            "androidx.documentfile:documentfile",
            "androidx.drawerlayout:drawerlayout",
            "androidx.emoji:emoji",
            "androidx.emoji:emoji-bundled",
            "androidx.emoji:emoji-appcompat",
            "androidx.enterprise:enterprise-feedback",
            "androidx.exifinterface:exifinterface",
            "androidx.fragment:fragment",
            "androidx.fragment:fragment-ktx",
            "androidx.fragment:fragment-testing",
            "androidx.gridlayout:gridlayout",
            "androidx.heifwriter:heifwriter",
            "androidx.hilt:hilt-*",
            "androidx.interpolator:interpolator",
            "androidx.leanback:leanback",
            "androidx.leanback:leanback-preference",
            "androidx.legacy:legacy-support-core-ui",
            "androidx.legacy:legacy-support-core-utils",
            "androidx.legacy:legacy-support-v4",
            "androidx.legacy:legacy-support-v13",
            "androidx.legacy:legacy-preference-v14",
            "androidx.lifecycle:lifecycle-extensions",
            "androidx.lifecycle:lifecycle-runtime",
            "androidx.lifecycle:lifecycle-viewmodel-ktx",
            "androidx.lifecycle:lifecycle-livedata",
            "androidx.lifecycle:lifecycle-common-java8",
            "androidx.lifecycle:lifecycle-viewmodel",
            "androidx.lifecycle:lifecycle-common",
            "androidx.lifecycle:lifecycle-livedata-ktx",
            "androidx.lifecycle:lifecycle-viewmodel-savedstate",
            "androidx.lifecycle:lifecycle-runtime-ktx",
            "androidx.lifecycle:lifecycle-livedata-core",
            "androidx.lifecycle:lifecycle-reactivestreams-ktx",
            "androidx.lifecycle:lifecycle-reactivestreams",
            "androidx.lifecycle:lifecycle-service",
            "androidx.lifecycle:lifecycle-livedata-core-ktx",
            "androidx.lifecycle:lifecycle-process",
            "androidx.lifecycle:lifecycle-compiler",
            "androidx.lifecycle:lifecycle-runtime-testing",
            "androidx.loader:loader",
            "androidx.localbroadcastmanager:localbroadcastmanager",
            "androidx.media:media",
            "androidx.media:media-widget",
            "androidx.media2:media2",
            "androidx.media2:media2-session",
            "androidx.media2:media2-player",
            "androidx.media2:media2-widget",
            "androidx.media2:media2-common",
            "androidx.media2:media2-exoplayer",
            "androidx.mediarouter:mediarouter",
            "androidx.navigation:navigation-fragment-ktx",
            "androidx.navigation:navigation-ui-ktx",
            "androidx.navigation:navigation-fragment",
            "androidx.navigation:navigation-runtime",
            "androidx.navigation:navigation-ui",
            "androidx.navigation:navigation-runtime-ktx",
            "androidx.navigation:navigation-common",
            "androidx.navigation:navigation-common-ktx",
            "androidx.navigation:navigation-dynamic-features-runtime",
            "androidx.navigation:navigation-safe-args-generator",
            "androidx.navigation:navigation-safe-args-gradle-plugin",
            "androidx.navigation:navigation-testing",
            "androidx.navigation:navigation-dynamic-features-fragment",
            "androidx.paging:paging-runtime",
            "androidx.paging:paging-runtime-ktx",
            "androidx.paging:paging-common",
            "androidx.paging:paging-rxjava2",
            "androidx.paging:paging-common-ktx",
            "androidx.paging:paging-rxjava2-ktx",
            "androidx.palette:palette",
            "androidx.palette:palette-ktx",
            "androidx.percentlayout:percentlayout",
            "androidx.preference:preference",
            "androidx.preference:preference-ktx",
            "androidx.print:print",
            "androidx.recommendation:recommendation",
            "androidx.recyclerview:recyclerview",
            "androidx.recyclerview:recyclerview-selection",
            "androidx.remotecallback:remotecallback",
            "androidx.remotecallback:remotecallback-processor",
            "androidx.room:room-runtime",
            "androidx.room:room-rxjava2",
            "androidx.room:room-ktx",
            "androidx.room:room-common",
            "androidx.room:room-testing",
            "androidx.room:room-coroutines",
            "androidx.room:room-guava",
            "androidx.room:room-migration",
            "androidx.room:room-compiler",
            "androidx.room:rxjava2",
            "androidx.savedstate:savedstate",
            "androidx.savedstate:savedstate-common",
            "androidx.savedstate:savedstate-bundle",
            "androidx.security:security-crypto",
            "androidx.sharetarget:sharetarget",
            "androidx.slidingpanelayout:slidingpanelayout",
            "androidx.sqlite:sqlite",
            "androidx.sqlite:sqlite-framework",
            "androidx.sqlite:sqlite-ktx",
            "androidx.swiperefreshlayout:swiperefreshlayout",
            "androidx.textclassifier:textclassifier",
            "androidx.transition:transition",
            "androidx.ui:ui-core",
            "androidx.ui:ui-util",
            "androidx.ui:ui-framework",
            "androidx.ui:ui-layout",
            "androidx.ui:ui-animation",
            "androidx.ui:ui-platform",
            "androidx.ui:ui-text",
            "androidx.ui:ui-vector",
            "androidx.ui:ui-foundation",
            "androidx.ui:ui-material",
            "androidx.ui:ui-animation-core",
            "androidx.ui:ui-tooling",
            "androidx.ui:ui-unit",
            "androidx.ui:ui-material-icons-core",
            "androidx.ui:ui-graphics",
            "androidx.ui:ui-geometry",
            "androidx.ui:ui-android-text",
            "androidx.ui:ui-material-icons-extended",
            "androidx.ui:ui-test",
            "androidx.versionedparcelable:versionedparcelable",
            "androidx.vectordrawable:vectordrawable",
            "androidx.vectordrawable:vectordrawable-animated",
            "androidx.viewpager:viewpager",
            "androidx.viewpager2:viewpager2",
            "androidx.wear:wear",
            "androidx.webkit:webkit",
            "androidx.work:work-runtime",
            "androidx.work:work-runtime-ktx",
            "androidx.work:work-rxjava2",
            "androidx.work:work-gcm",
            "androidx.work:work-testing"
        ),
        "Android Jetpack"
    )
    PresetGroups += LibraryGroup(
        artifacts = listOf(
            "androidx.databinding:databinding-adapters",
            "androidx.databinding:databinding-runtime",
            "androidx.databinding:databinding-common"
        ),
        name = "AndroidX DataBinding",
        author = "The Android Open Source Project",
        url = "https://developer.android.com/studio",
        description = null,
        licenses = null
    )
    PresetGroups += LibraryGroup(
        listOf(
            "androidx.test:core",
            "androidx.test:runner",
            "androidx.test:rules",
            "androidx.test:monitor",
            "androidx.test:core-ktx",
            "androidx.test:orchestrator",
            "androidx.test.espresso:espresso-core",
            "androidx.test.espresso:espresso-idling-resource",
            "androidx.test.espresso:espresso-intents",
            "androidx.test.espresso:espresso-contrib",
            "androidx.test.espresso:espresso-web",
            "androidx.test.espresso:espresso-accessibility",
            "androidx.test.espresso:espresso-remote",
            "androidx.test.ext:junit",
            "androidx.test.ext:truth",
            "androidx.test.ext:junit-ktx",
            "androidx.test.services:storage",
            "androidx.test.services:test-services"
        ),
        "AndroidX Test Library"
    )
    PresetGroups += LibraryGroup(
        listOf("com.android.installreferrer:installreferrer"),
        "Play Install Referrer Library"
    )
        .addUrl("https://developer.android.com/google/play/installreferrer/library")
        .addAuthor("Google Inc.")
    PresetGroups += LibraryGroup(
        listOf(
            "com.android.billingclient:billing",
            "com.android.billingclient:billing-ktx"
        ),
        "Google Play Billing Library"
    )
        .addUrl("https://developer.android.com/google/play/billing/billing_overview")
        .addAuthor("Google Inc.")
    PresetGroups += LibraryGroup(
        listOf(
            "com.google.android.play:core",
            "com.google.android.play:core-ktx"
        ),
        "Google Play Core Library"
    )
        .addUrl("https://developer.android.com/guide/playcore")
        .addAuthor("Google Inc.")
}