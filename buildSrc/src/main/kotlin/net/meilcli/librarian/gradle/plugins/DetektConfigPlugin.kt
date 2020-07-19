package net.meilcli.librarian.gradle.plugins

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import net.meilcli.librarian.gradle.dependencies.Dependencies
import net.meilcli.librarian.gradle.dependencies.Detekt
import net.meilcli.librarian.gradle.dependencies.MobileAct
import net.meilcli.librarian.gradle.extensions.detektPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import java.io.File

class DetektConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.findByType(DetektExtension::class.java)?.apply {
            toolVersion = Dependencies.detektVersion
            buildUponDefaultConfig = true
            parallel = true
            config = project.files("${project.rootProject.rootDir}/detekt.yml")
            reports.apply {
                xml.enabled = true
                val name = project.projectDir.toRelativeString(project.rootProject.rootDir).replace("/", "_")
                xml.destination = File("${project.rootProject.rootDir}/reports/detekt/${name}.xml")
            }
        }
        project.dependencies {
            detektPlugins(Detekt.formatting)
            detektPlugins(MobileAct.retekt)
        }
    }
}