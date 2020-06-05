package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.configurations.ContainConfiguration
import net.meilcli.librarian.plugin.configurations.ExactConfiguration
import net.meilcli.librarian.plugin.entities.ConfigurationArtifacts
import net.meilcli.librarian.plugin.internal.IFilter

class ConfigurationArtifactsByPageFilter(
    private val pageExtension: LibrarianPageExtension
) : IFilter<List<ConfigurationArtifacts>> {

    override fun filter(source: List<ConfigurationArtifacts>): List<ConfigurationArtifacts> {
        return source.filter { filter(it) }
    }

    private fun filter(configurationArtifacts: ConfigurationArtifacts): Boolean {
        for (configuration in pageExtension.configurations.value) {
            if (configuration is ContainConfiguration) {
                if (configuration.value.isEmpty()) {
                    continue
                }
                if (configurationArtifacts.configurationNames.all { configuration.value.contains(it.name) }) {
                    return true
                }
            }
            if (configuration is ExactConfiguration) {
                if (configurationArtifacts.configurationNames.map { it.name } == configuration.value) {
                    return true
                }
            }
        }

        return false
    }
}