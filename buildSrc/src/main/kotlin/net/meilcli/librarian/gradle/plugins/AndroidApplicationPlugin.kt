package net.meilcli.librarian.gradle.plugins

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.ProguardFiles
import net.meilcli.librarian.gradle.Dependencies
import net.meilcli.librarian.gradle.extensions.androidTestImplementation
import net.meilcli.librarian.gradle.extensions.implementation
import net.meilcli.librarian.gradle.extensions.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val plugin = checkNotNull(project.plugins.findPlugin(AppPlugin::class.java)) { "must set android library plugin" }
        val applicationExtension = project.extensions.create("androidApplication", AndroidApplicationExtension::class.java)

        plugin.apply {
            extension.compileSdkVersion(29)

            extension.defaultConfig {
                minSdkVersion(15)
                targetSdkVersion(29)
                applicationId = "net.meilcli.librarian.sample.${applicationExtension.applicationIdPostfix}"
                versionCode = 1
                versionName = "1.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            (extension.buildTypes.findByName("release")
                ?: extension.buildTypes.create("release")).apply {
                isMinifyEnabled = false
                proguardFiles(
                    ProguardFiles.getDefaultProguardFile("proguard-android-optimize.txt", project),
                    "proguard-rules.pro"
                )
            }

            extension.sourceSets.all {
                java.srcDir("src/${name}/kotlin")
            }
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