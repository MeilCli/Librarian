package net.meilcli.librarian.gradle.plugins

import com.android.build.gradle.LibraryPlugin
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
        project.afterEvaluate {
            val publication = extension.publications.findByName("pluginMaven") as? MavenPublication
            extension.publications {
                // escape multiple attach Publication
                val attach: (MavenPublication) -> Unit = {
                    it.groupId = "net.meilcli.librarian"
                    it.version = versionValue

                    it.pom {
                        name.set("Librarian")
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
                    if (publication == null) {
                        // component can include one time
                        if (project.plugins.hasPlugin(LibraryPlugin::class.java)) {
                            it.from(project.components["release"])
                        } else {
                            it.from(project.components["java"])
                        }
                    }
                }

                if (publication != null) {
                    attach(publication)
                } else {
                    register("gpr", MavenPublication::class.java) {
                        attach(this)
                    }
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
                if (publication == null) {
                    setPublications("gpr")
                } else {
                    setPublications("pluginMaven")
                }
                publish = true
                override = true
            }
        }
    }
}