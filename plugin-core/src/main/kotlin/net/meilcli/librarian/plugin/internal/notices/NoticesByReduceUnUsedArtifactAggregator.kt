package net.meilcli.librarian.plugin.internal.notices

import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.IAggregator2

class NoticesByReduceUnUsedArtifactAggregator : IAggregator2<List<Notice>, List<ConfigurationArtifact>, List<Notice>> {

    override fun aggregate(
        source1: List<Notice>,
        source2: List<ConfigurationArtifact>
    ): List<Notice> {
        return source1.map {
            Notice(
                it.artifacts.filter { artifact -> containsArtifact(source2, artifact) },
                name = it.name,
                author = it.author,
                url = it.url,
                description = it.description,
                licenses = it.licenses
            )
        }
    }

    private fun containsArtifact(configurationArtifacts: List<ConfigurationArtifact>, artifact: String): Boolean {
        for (configurationArtifact in configurationArtifacts) {
            if (configurationArtifact.artifacts.any { it.artifact == artifact }) {
                return true
            }
        }
        return false
    }
}