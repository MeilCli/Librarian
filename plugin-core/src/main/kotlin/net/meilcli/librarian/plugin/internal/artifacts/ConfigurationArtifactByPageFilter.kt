package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.internal.IFilter
import org.slf4j.LoggerFactory

class ConfigurationArtifactByPageFilter(
    private val pageExtension: LibrarianPageExtension
) : IFilter<List<ConfigurationArtifact>> {

    private val logger = LoggerFactory.getLogger(ConfigurationArtifactByPageFilter::class.java)

    override fun filter(source: List<ConfigurationArtifact>): List<ConfigurationArtifact> {
        val result = mutableListOf<ConfigurationArtifact>()

        for (configuration in pageExtension.configurations) {
            val configurationArtifact = source.firstOrNull { it.configurationName == configuration }
            if (configurationArtifact == null) {
                logger.warn("Librarian cannot resolve unknown configuration: $configuration")
                continue
            }
            result.add(configurationArtifact)
        }

        return result
    }
}