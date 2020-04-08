package net.meilcli.librarian.plugin.internal.pomprojects

import com.tickaroo.tikxml.TikXml
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.PomProject
import okio.buffer
import okio.source
import org.gradle.api.Project
import org.gradle.api.artifacts.result.ResolvedArtifactResult
import org.gradle.api.internal.artifacts.DefaultModuleIdentifier
import org.gradle.internal.component.external.model.DefaultModuleComponentIdentifier
import org.gradle.maven.MavenModule
import org.gradle.maven.MavenPomArtifact
import org.slf4j.LoggerFactory
import java.io.File

class MavenPomProjectLoader : IPomProjectLoader {

    private val logger = LoggerFactory.getLogger(MavenPomProjectLoader::class.java)

    private val pomCache = mutableMapOf<Artifact, PomProject>()

    override fun load(project: Project, artifact: Artifact): PomProject? {
        var result = pomCache[artifact]
        if (result != null) {
            return result
        }
        try {
            result = artifact.getPomFile(project)?.loadPomProject(artifact)
            result = result?.toOverridePomProjectIfNeeded(project)
            if (result != null) {
                pomCache[artifact] = result
            }
            return result
        } catch (exception: Exception) {
            logger.warn("Librarian cannot resolve pom file: ${artifact.group}:${artifact.name}:${artifact.version}")
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
            this.source().use {
                it.buffer().use { buffer ->
                    val parser = TikXml.Builder()
                        .exceptionOnUnreadXml(false)
                        .build()
                    return parser.read(buffer, PomProject::class.java).apply {
                        this.group = this.group ?: artifact.group
                        this.artifact = this.artifact ?: artifact.name
                        this.version = this.version ?: artifact.version
                    }
                }
            }
        } catch (exception: Exception) {
            logger.warn("Librarian okio error", exception)
            return null
        }
    }

    // try override name, url, description
    private fun PomProject.toOverridePomProjectIfNeeded(project: Project): PomProject {
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
            val parentProject = load(project, Artifact(parentGroup, parentArtifact, parentVersion))
            return PomProject(
                group = group,
                artifact = artifact,
                version = version,
                parent = parent,
                name = name ?: parentProject?.name,
                url = url ?: parentProject?.url,
                description = description ?: parentProject?.description,
                developers = developers ?: parentProject?.developers,
                licenses = licenses ?: parentProject?.licenses
            )
        } catch (exception: Exception) {
            return this
        }
    }
}