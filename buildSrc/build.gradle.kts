plugins {
    `kotlin-dsl`
}

apply(from = "./dependencies/build.gradle")

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.github.gmazzo:gradle-buildconfig-plugin:2.0.1")
    }
}

repositories {
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:3.6.2")
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
}