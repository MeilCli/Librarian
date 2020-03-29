package net.meilcli.librarian.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.ModuleVersionIdentifier
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.api.artifacts.result.ResolvedArtifactResult
import org.gradle.api.internal.artifacts.DefaultModuleIdentifier
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.component.external.model.DefaultModuleComponentIdentifier
import org.gradle.maven.MavenModule
import org.gradle.maven.MavenPomArtifact
import org.slf4j.LoggerFactory
import java.io.File

open class LoadDependenciesTask : DefaultTask() {

    companion object {

        private const val unspecifiedVersion = "unspecified"
    }

    private val logger = LoggerFactory.getLogger(LoadDependenciesTask::class.java)

    //@Input
    //var configurations: ConfigurationContainer? = null

    @OutputFile
    var outputFile: File? = null

    @TaskAction
    fun action() {
        val configurations = project.configurations ?: return
        val sb = StringBuilder()
        for (configuration in configurations) {
            sb.appendln("--- configuration: ${configuration.name}, ${configuration.isCanBeResolved} ---")
            if (configuration.isCanBeResolved.not()) {
                logger.debug("${configuration.name} is not canBeResolved")
                continue
            }
            try {
                val resolvedDependencies = configuration.resolvedConfiguration
                    .lenientConfiguration
                    .firstLevelModuleDependencies

                for (a in configuration.resolvedConfiguration.lenientConfiguration.firstLevelModuleDependencies) {
                    sb.appendln("first: ${a.name}")
                    if (a.moduleVersion == unspecifiedVersion) {
                        sb.appendln("unspecified")

                        if (a.moduleGroup == project.rootProject.name) {
                            sb.appendln("other module")
                            val subProject = project.findProject(":${a.moduleName}")
                            sb.appendln("other found: ${subProject != null}")
                        }
                        for (c in a.children.getResolvedArtifacts()) {
                            sb.appendln("child: ${c.name}")
                        }
                    }
                }
                val resolvedArtifacts = resolvedDependencies.getResolvedArtifacts()
                for (resolvedArtifact in resolvedArtifacts) {
                    sb.appendln("${resolvedArtifact.moduleVersion.id.group}:${resolvedArtifact.moduleVersion.id.name}")
                    val pomFile = resolvedArtifact.moduleVersion.id.getPomFile() ?: continue
                    logger.info("${resolvedArtifact.moduleVersion.id.group}:${resolvedArtifact.moduleVersion.id.name}:${resolvedArtifact.moduleVersion.id.version} is ${pomFile.absolutePath}")
                }
            } catch (exception: Exception) {
                logger.warn("Failed Librarian ${configuration.name}", exception)
            }
        }
        outputFile?.writeText(sb.toString(), Charsets.UTF_8)
    }

    private fun Set<ResolvedDependency>.getResolvedArtifacts(): List<ResolvedArtifact> {
        try {
            return this.flatMap {
                if (it.moduleVersion == unspecifiedVersion) {
                    it.children.getResolvedArtifacts()
                } else {
                    it.moduleArtifacts
                    //it.allModuleArtifacts
                }
            }
        } catch (exception: Exception) {
            logger.warn("Failed Librarian $name", exception)
        }
        return emptyList()
    }

    private fun ModuleVersionIdentifier.getPomFile(): File? {
        val moduleComponentIdentifier = DefaultModuleComponentIdentifier(
            DefaultModuleIdentifier.newId(group, name),
            version
        )
        val components = project.dependencies
            .createArtifactResolutionQuery()
            .forComponents(moduleComponentIdentifier)
            .withArtifacts(MavenModule::class.java, MavenPomArtifact::class.java)
            .execute()
        if (components.resolvedComponents.isEmpty()) {
            logger.warn("$moduleComponentIdentifier has no PomFile")
            return null
        }

        val artifacts =
            components.resolvedComponents.first().getArtifacts(MavenPomArtifact::class.java)
        if (artifacts.isEmpty()) {
            logger.warn("$moduleComponentIdentifier not handle ${artifacts.first().javaClass}")
            return null
        }

        val artifact = artifacts.first()
        if (artifact !is ResolvedArtifactResult) {
            logger.warn("$moduleComponentIdentifier not handle ${artifacts.first().javaClass}")
            return null
        }

        return artifact.file
    }
}