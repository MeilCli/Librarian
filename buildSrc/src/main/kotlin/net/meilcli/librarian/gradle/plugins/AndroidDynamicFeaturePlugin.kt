package net.meilcli.librarian.gradle.plugins

import com.android.build.gradle.BaseExtension
import net.meilcli.librarian.gradle.dependencies.Android
import net.meilcli.librarian.gradle.dependencies.Kotlin
import net.meilcli.librarian.gradle.extensions.applyLintSetting
import net.meilcli.librarian.gradle.extensions.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDynamicFeaturePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.findByName("android") as BaseExtension
        extension.compileSdkVersion(29)

        extension.defaultConfig {
            minSdkVersion(15)
            targetSdkVersion(29)
            versionCode = 1
            versionName = "1.0"
        }

        project.applyLintSetting()

        extension.sourceSets.all {
            java.srcDir("src/${name}/kotlin")
        }

        project.dependencies {
            implementation(Kotlin.stdlib)
            implementation(Android.appCompat)
        }
    }
}