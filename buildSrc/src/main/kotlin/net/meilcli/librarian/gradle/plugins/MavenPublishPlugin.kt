package net.meilcli.librarian.gradle.plugins

import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get
import java.io.File
import java.net.URI
import java.util.*

class MavenPublishPlugin : Plugin<Project> {

    @Suppress("UnstableApiUsage")
    override fun apply(project: Project) {
        val extension = checkNotNull(project.extensions.findByType(PublishingExtension::class.java))

        val versionPropertyFile = File(project.rootProject.rootDir, "buildSrc/version.properties")
        val versionProperty = Properties()
        val versionValue = if (versionPropertyFile.exists()) {
            versionProperty.load(versionPropertyFile.inputStream())
            versionProperty.getProperty("gpr.version") ?: "0.0.1"
        } else {
            "0.0.1"
        }

        extension.repositories {
            maven {
                name = "GitHubPackages"
                url = URI("https://maven.pkg.github.com/MeilCli/Librarian")

                credentials {
                    username = project.findProperty("gpr.user") as? String ?: System.getenv("GITHUB_USER")
                    password = project.findProperty("gpr.token") as? String ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
        extension.publications {
            register("gpr", MavenPublication::class.java) {
                groupId = "net.meilcli.librarian"
                version = versionValue

                pom {
                    url.set("https://github.com/MeilCli/Librarian")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://github.com/MeilCli/Librarian/blob/master/LICENSE")
                        }
                    }
                    developers {
                        developer {
                            name.set("MeilCli")
                        }
                    }
                }

                from(project.components["java"])
            }
        }

        val bintray = checkNotNull(project.extensions.findByType(BintrayExtension::class.java))
        bintray.apply {
            user = project.findProperty("bintray.user") as? String ?: System.getenv("BINTRAY_USER")
            key = project.findProperty("bintray.token") as? String ?: System.getenv("BINTRAY_TOKEN")
            pkg.apply {
                userOrg = "meilcli"
                repo = "librarian"
                name = project.name
                setLicenses("MIT")
                githubRepo = "MeilCli/Librarian"
                websiteUrl = "https://github.com/MeilCli/Librarian"
                vcsUrl = "https://github.com/MeilCli/Librarian.git"

                version.name = versionValue
            }
            setPublications("gpr")
            publish = true
            override = true
        }
    }
}