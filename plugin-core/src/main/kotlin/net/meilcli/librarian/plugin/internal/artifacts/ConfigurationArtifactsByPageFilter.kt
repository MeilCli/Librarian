package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.configurations.ContainConfiguration
import net.meilcli.librarian.plugin.configurations.ExactConfiguration
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.internal.IFilter
import org.slf4j.LoggerFactory

class ConfigurationArtifactsByPageFilter(
    private val pageExtension: LibrarianPageExtension
) : IFilter<List<ConfigurationArtifact>> {

    private val logger = LoggerFactory.getLogger(ConfigurationArtifactsByPageFilter::class.java)

    override fun filter(source: List<ConfigurationArtifact>): List<ConfigurationArtifact> {
        val result = mutableListOf<ConfigurationArtifact>()

        for (configurationArtifact in source) {
            if (filter(configurationArtifact)) {
                result += configurationArtifact
            }
        }

        return result
    }

    private fun filter(configurationArtifact: ConfigurationArtifact): Boolean {
        for (configuration in pageExtension.configurations.value) {
            if (configuration is ContainConfiguration) {
                if (configuration.value.isEmpty()) {
                    continue
                }
                if (configurationArtifact.configurationNames.all { configuration.value.contains(it) }) {
                    return true
                }
            }
            if (configuration is ExactConfiguration) {
                if (configurationArtifact.configurationNames == configuration.value) {
                    return true
                }
            }
        }

        return false
    }
}