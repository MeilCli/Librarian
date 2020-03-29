package net.meilcli.librarian.gradle.plugins

import net.meilcli.librarian.gradle.Dependencies
import net.meilcli.librarian.gradle.extensions.implementation
import net.meilcli.librarian.gradle.extensions.testImplementation
import net.meilcli.librarian.gradle.extensions.testRuntimeOnly
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies

class KotlinLibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.named("test", Test::class.java).configure {
            @Suppress("UnstableApiUsage")
            useJUnitPlatform()
        }

        project.dependencies {
            implementation(Dependencies.Kotlin.stdlib)

            testImplementation(Dependencies.Junit5.api)
            testRuntimeOnly(Dependencies.Junit5.engine)
        }
    }
}