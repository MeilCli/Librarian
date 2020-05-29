package net.meilcli.librarian.plugin.internal.pomprojects

import kotlinx.serialization.modules.EmptyModule
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.PomProject
import net.meilcli.librarian.plugin.internal.IParameterizedLoader
import nl.adaptivity.xmlutil.serialization.XML
import org.gradle.api.Project
import org.gradle.api.artifacts.result.ResolvedArtifactResult
import org.gradle.api.internal.artifacts.DefaultModuleIdentifier
import org.gradle.internal.component.external.model.DefaultModuleComponentIdentifier
import org.gradle.maven.MavenModule
import org.gradle.maven.MavenPomArtifact
import org.slf4j.LoggerFactory
import java.io.File

class MavenPomProjectLoader(
    private val project: Project
) : IParameterizedLoader<Artifact, PomProject?> {

    private val logger = LoggerFactory.getLogger(MavenPomProjectLoader::class.java)

    private val pomCache = mutableMapOf<Artifact, PomProject>()

    override fun load(parameter: Artifact): PomProject? {
        var result = pomCache[parameter]
        if (result != null) {
            return result
        }
        try {
            result = parameter.getPomFile(project)?.loadPomProject(parameter)
            result = result?.toOverridePomProjectIfNeeded()
            if (result != null) {
                pomCache[parameter] = result
            }
            return result
        } catch (exception: Exception) {
            logger.warn("Librarian cannot resolve pom file: ${parameter.group}:${parameter.name}:${parameter.version}")
        }
        return null
    }

    private fun Artifact.getPomFile(project: Project): File? {
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

    private fun File.loadPomProject(artifact: Artifact): PomProject? {
        try {
            val text = readText(Charsets.UTF_8)
            val xml = XML(EmptyModule) {
                this.unknownChildHandler = { _, _, _, _ -> }
            }
            return xml.parse(PomProject.serializer(), text).apply {
                this.group = this.group ?: artifact.group
                this.artifact = this.artifact ?: artifact.name
                this.version = this.version ?: artifact.version
            }
        } catch (exception: Exception) {
            logger.warn("Librarian okio error", exception)
            return null
        }
    }

    // try override name, url, description
    private fun PomProject.toOverridePomProjectIfNeeded(): PomProject {
        val parentGroup = parent?.group ?: return this
        val parentArtifact = parent.artifact ?: return this
        val parentVersion = parent.version ?: return this

        if (name != null &&
            url != null &&
            description != null &&
            developers != null &&
            licenses != null
        ) {
            return this
        }

        try {
            val parentProject = load(Artifact(parentGroup, parentArtifact, parentVersion))
            return PomProject(
                group = group,
                artifact = artifact,
                version = version,
                parent = parent,
                name = name ?: parentProject?.name,
                url = url ?: parentProject?.url,
                description = description ?: parentProject?.description,
                organization = organization ?: parentProject?.organization,
                developers = developers ?: parentProject?.developers,
                licenses = licenses ?: parentProject?.licenses
            )
        } catch (exception: Exception) {
            return this
        }
    }
}