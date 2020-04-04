package net.meilcli.librarian.gradle.plugins

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import net.meilcli.librarian.gradle.Dependencies
import net.meilcli.librarian.gradle.extensions.androidTestImplementation
import net.meilcli.librarian.gradle.extensions.implementation
import net.meilcli.librarian.gradle.extensions.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.findByName("android") as BaseExtension
        extension.compileSdkVersion(29)

        extension.defaultConfig {
            minSdkVersion(15)
            targetSdkVersion(29)
            versionCode = 1
            versionName = "1.0"
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFile("consumer-rules.pro")
        }

        (extension.buildTypes.findByName("release")
            ?: extension.buildTypes.create("release")).apply {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt", project),
                "proguard-rules.pro"
            )
        }

        extension.sourceSets.all {
            java.srcDir("src/${name}/kotlin")
        }

        project.dependencies {
            implementation(Dependencies.Kotlin.stdlib)
            implementation(Dependencies.Android.appCompat)

            testImplementation(Dependencies.Junit4.junit)

            androidTestImplementation(Dependencies.Android.espresso)
            androidTestImplementation(Dependencies.Android.junit)
        }
    }
}