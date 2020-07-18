package net.meilcli.librarian.gradle.extensions

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import java.io.File

fun Project.applyLintSetting() {
    val extension = project.extensions.findByName("android") as? BaseExtension ?: return

    extension.lintOptions {
        xmlReport = true
        val name = project.projectDir.toRelativeString((project.rootProject.rootDir)).replace("/", "_")
        xmlOutput = File("${project.rootProject.rootDir}/reports/lint/${name}.xml")
    }
}