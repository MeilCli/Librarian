package net.meilcli.librarian.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get
import java.net.URI

class MavenPublishPlugin : Plugin<Project> {

    @Suppress("UnstableApiUsage")
    override fun apply(project: Project) {
        val extension = checkNotNull(project.extensions.findByType(PublishingExtension::class.java))
        extension.repositories {
            maven {
                name = "GitHubPackages"
                url = URI("https://maven.pkg.github.com/MeilCli/Librarian")

                credentials {
                    username = project.findProperty("gpr.user") as? String ?: System.getenv("USER_NAME")
                    password = project.findProperty("gpr.token") as? String ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
        extension.publications {
            register("gpr", MavenPublication::class.java) {
                groupId = "net.meilcli.librarian"
                version = project.findProperty("gpr.version") as? String ?: "0.0.1"

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
    }
}