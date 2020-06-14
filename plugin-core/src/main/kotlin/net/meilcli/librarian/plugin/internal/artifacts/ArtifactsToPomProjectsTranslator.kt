package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.IPomProject
import net.meilcli.librarian.plugin.internal.IParameterizedLoader
import net.meilcli.librarian.plugin.internal.ITranslator
import org.slf4j.LoggerFactory

class ArtifactsToPomProjectsTranslator(
    private val pomProjectLoader: IParameterizedLoader<Artifact, IPomProject?>
) : ITranslator<Collection<Artifact>, List<IPomProject>> {

    private val logger = LoggerFactory.getLogger(ArtifactsToPomProjectsTranslator::class.java)

    override fun translate(source: Collection<Artifact>): List<IPomProject> {
        val result = mutableListOf<IPomProject>()

        for (entity in source) {
            val pomProject = pomProjectLoader.load(entity)
            if (pomProject == null) {
                logger.warn("Librarian cannot found pom: ${entity.group}:${entity.name}:${entity.version}")
            } else {
                result += pomProject
            }
        }

        return result
    }
}