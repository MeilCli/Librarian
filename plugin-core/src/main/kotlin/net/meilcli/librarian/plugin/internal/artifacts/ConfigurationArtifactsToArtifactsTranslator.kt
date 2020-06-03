package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.internal.ITranslator

class ConfigurationArtifactsToArtifactsTranslator : ITranslator<Sequence<ConfigurationArtifact>, Set<Artifact>> {

    override fun translate(source: Sequence<ConfigurationArtifact>): Set<Artifact> {
        val result = mutableSetOf<Artifact>()
        for (entity in source) {
            result.addAll(entity.artifacts)
        }
        return result
    }
}