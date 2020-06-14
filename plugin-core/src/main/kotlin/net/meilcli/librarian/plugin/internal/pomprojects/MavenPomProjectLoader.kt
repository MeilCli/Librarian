package net.meilcli.librarian.plugin.internal.pomprojects

import kotlinx.serialization.modules.EmptyModule
import net.meilcli.librarian.plugin.entities.*
import net.meilcli.librarian.plugin.internal.IParameterizedLoader
import nl.adaptivity.xmlutil.XmlException
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
) : IParameterizedLoader<Artifact, IPomProject?> {

    private val logger = LoggerFactory.getLogger(MavenPomProjectLoader::class.java)

    private val pomCache = mutableMapOf<Artifact, IPomProject>()

    override fun load(parameter: Artifact): IPomProject? {
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

    private fun File.loadPomProject(artifact: Artifact): IPomProject? {
        try {
            val text = readText(Charsets.UTF_8)
            val xml = XML(EmptyModule) {
                this.unknownChildHandler = { _, _, _, _ -> }
            }
            return try {
                xml.parse(PomProject.serializer(), text).apply {
                    this.group = this.group ?: artifact.group
                    this.artifact = this.artifact ?: artifact.name
                    this.version = this.version ?: artifact.version
                }
            } catch (exception: XmlException) {
                xml.parse(PomProjectNoNameSpace.serializer(), text).apply {
                    this.group = this.group ?: artifact.group
                    this.artifact = this.artifact ?: artifact.name
                    this.version = this.version ?: artifact.version
                }
            }
        } catch (exception: Exception) {
            logger.warn("Librarian okio error", exception)
            return null
        }
    }

    // try override name, url, description
    private fun IPomProject.toOverridePomProjectIfNeeded(): IPomProject {
        val parentGroup = parent?.group ?: return this
        val parentArtifact = parent?.artifact ?: return this
        val parentVersion = parent?.version ?: return this

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
            return object : IPomProject {
                override var group: String? = this@toOverridePomProjectIfNeeded.group
                override var artifact: String? = this@toOverridePomProjectIfNeeded.artifact
                override var version: String? = this@toOverridePomProjectIfNeeded.version
                override var name: String? = this@toOverridePomProjectIfNeeded.name ?: parentProject?.name
                override var description: String? = this@toOverridePomProjectIfNeeded.description ?: parentProject?.description
                override var url: String? = this@toOverridePomProjectIfNeeded.url ?: parentProject?.url
                override val parent: IPomParentProject? = this@toOverridePomProjectIfNeeded.parent
                override val organization: IPomOrganization? = this@toOverridePomProjectIfNeeded.organization ?: parentProject?.organization
                override val licenses: List<IPomLicense>? = this@toOverridePomProjectIfNeeded.licenses ?: parentProject?.licenses
                override val developers: List<IPomDeveloper>? = this@toOverridePomProjectIfNeeded.developers ?: parentProject?.developers
            }
        } catch (exception: Exception) {
            return this
        }
    }
}