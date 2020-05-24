package net.meilcli.librarian.plugin.internal.bintray

import net.meilcli.librarian.plugin.entities.*
import net.meilcli.librarian.plugin.internal.IAggregator1
import net.meilcli.librarian.plugin.internal.ILoader
import net.meilcli.librarian.plugin.internal.IParameterizedLoader
import net.meilcli.librarian.plugin.internal.Placeholder
import org.slf4j.LoggerFactory

class BintrayAggregator(
    private val bintrayRepositoryLoader: IParameterizedLoader<Artifact, BintrayRepository?>,
    private val bintrayPackageLoader: IParameterizedLoader<Pair<Artifact, BintrayRepository>, BintrayPackage?>,
    private val bintrayLicensesLoader: ILoader<List<BintrayLicense>?>
) : IAggregator1<Collection<Artifact>, List<LibraryGroup>> {

    private val logger = LoggerFactory.getLogger(BintrayAggregator::class.java)

    override fun aggregate(source: Collection<Artifact>): List<LibraryGroup> {
        val licenses = bintrayLicensesLoader.load()
        if (licenses == null) {
            logger.warn("librarian failed to fetch bintray licenses")
            return emptyList()
        }

        val bintrayPackages = mutableListOf<Pair<Artifact, BintrayPackage>>()

        for (artifact in source) {
            val bintrayRepository = bintrayRepositoryLoader.load(artifact)
            if (bintrayRepository != null) {
                val bintrayPackage = bintrayPackageLoader.load(Pair(artifact, bintrayRepository))
                if (bintrayPackage != null) {
                    bintrayPackages += Pair(artifact, bintrayPackage)
                }
            }
        }

        val aggregatePackages = bintrayPackages.filter { it.second.canAggregateToGroup() }
            .groupBy { it.second }
            .map { it.toLibraryGroup(licenses) }
            .filter { it.name != Placeholder.name }
        val noAggregatePackages = bintrayPackages.filter { it.second.canAggregateToGroup().not() }
            .map { it.toLibraryGroup(licenses) }
            .filter { it.name != Placeholder.name }

        return aggregatePackages + noAggregatePackages
    }

    private fun BintrayPackage.canAggregateToGroup(): Boolean {
        return githubRepository != null || vcsUrl != null || websiteUrl != null
    }

    private fun Pair<Artifact, BintrayPackage>.toLibraryGroup(licenses: List<BintrayLicense>): LibraryGroup {
        return LibraryGroup(
            artifacts = listOf(first.artifact),
            name = when {
                second.githubRepository != null -> second.githubRepository?.replace("/", "_") ?: Placeholder.name
                second.repository != "jcenter" -> second.repository
                else -> Placeholder.name
            },
            author = second.owner,
            description = second.description,
            url = when {
                second.githubRepository != null -> "https://github.com/${second.githubRepository}"
                second.websiteUrl != null -> second.websiteUrl
                second.vcsUrl != null -> second.vcsUrl
                else -> Placeholder.url
            },
            licenses = second.licenses.map { x -> licenses.first { y -> y.name == x }.toLicense() }
        )
    }

    private fun Map.Entry<BintrayPackage, List<Pair<Artifact, BintrayPackage>>>.toLibraryGroup(licenses: List<BintrayLicense>): LibraryGroup {
        return LibraryGroup(
            artifacts = value.map { it.first.artifact },
            name = when {
                key.githubRepository != null -> key.githubRepository?.replace("/", "_") ?: Placeholder.name
                key.repository != "jcenter" -> key.repository
                else -> Placeholder.name
            },
            author = key.owner,
            description = key.description,
            url = when {
                key.githubRepository != null -> "https://github.com/${key.githubRepository}"
                key.websiteUrl != null -> key.websiteUrl
                key.vcsUrl != null -> key.vcsUrl
                else -> Placeholder.url
            },
            licenses = key.licenses.map { x -> licenses.first { y -> y.name == x }.toLicense() }
        )
    }

    private fun BintrayLicense.toLicense(): License {
        return License(fullName, url)
    }
}