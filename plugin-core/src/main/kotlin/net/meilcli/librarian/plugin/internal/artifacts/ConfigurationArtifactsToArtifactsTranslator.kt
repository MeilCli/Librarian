package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.ConfigurationArtifacts
import net.meilcli.librarian.plugin.internal.ITranslator

class ConfigurationArtifactsToArtifactsTranslator : ITranslator<List<ConfigurationArtifacts>, Set<Artifact>> {

    override fun translate(source: List<ConfigurationArtifacts>): Set<Artifact> {
        val result = mutableSetOf<Artifact>()
        for (entity in source) {
            result.addAll(entity.artifacts)
        }
        return result
    }
}