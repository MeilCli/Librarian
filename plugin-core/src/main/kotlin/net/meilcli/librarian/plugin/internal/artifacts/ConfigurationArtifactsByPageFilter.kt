package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.configurations.ContainConfiguration
import net.meilcli.librarian.plugin.configurations.ExactConfiguration
import net.meilcli.librarian.plugin.entities.ConfigurationArtifact
import net.meilcli.librarian.plugin.internal.IFilter

class ConfigurationArtifactsByPageFilter(
    private val pageExtension: LibrarianPageExtension
) : IFilter<Sequence<ConfigurationArtifact>> {

    override fun filter(source: Sequence<ConfigurationArtifact>): Sequence<ConfigurationArtifact> {
        return source.filter { filter(it) }
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