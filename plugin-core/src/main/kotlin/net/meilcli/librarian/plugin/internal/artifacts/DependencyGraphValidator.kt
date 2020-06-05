package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.configurations.ContainConfiguration
import net.meilcli.librarian.plugin.configurations.ExactConfiguration
import net.meilcli.librarian.plugin.entities.DependencyGraph
import net.meilcli.librarian.plugin.internal.IValidator1

class DependencyGraphValidator(
    private val extension: LibrarianExtension
) : IValidator1<DependencyGraph> {

    override fun valid(element: DependencyGraph): Boolean {
        val filteredConfigurationCounts = mutableListOf<Int>()
        val configurationNames = aggregateConfigurationNames()

        for (projectMap in element.graph) {
            val count = projectMap.value
                .keys
                .filter { configurationNames.contains(it.name) }
                .size
            filteredConfigurationCounts += count
        }

        var count: Long = 1
        for (filteredConfigurationCount in filteredConfigurationCounts) {
            count *= if (0 < filteredConfigurationCount) filteredConfigurationCount else 1
        }

        return count < extension.failOnTooManyResolvingConfigurationLimit
    }

    private fun aggregateConfigurationNames(): Set<String> {
        val result = mutableSetOf<String>()

        for (page in extension.pages) {
            for (configuration in page.configurations.value) {
                result += when (configuration) {
                    is ExactConfiguration -> configuration.value
                    is ContainConfiguration -> configuration.value
                    else -> emptyList()
                }
            }
        }

        return result
    }
}