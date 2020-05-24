package net.meilcli.librarian.plugin.internal.bintray

import net.meilcli.librarian.plugin.entities.*
import net.meilcli.librarian.plugin.internal.IAggregator1
import net.meilcli.librarian.plugin.internal.ILoader
import net.meilcli.librarian.plugin.internal.IParameterizedLoader
import net.meilcli.librarian.plugin.internal.Placeholder
import org.slf4j.LoggerFactory

class BintrayAggregator(
    private val librariesLoader: ILoader<List<Library>>,
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

        val libraries = librariesLoader.load()
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
            .map { it.toLibraryGroup(libraries, licenses) }
            .filter { it.name != Placeholder.name }
        val noAggregatePackages = bintrayPackages.filter { it.second.canAggregateToGroup().not() }
            .map { it.toLibraryGroup(libraries, licenses) }
            .filter { it.name != Placeholder.name }

        return aggregatePackages + noAggregatePackages
    }

    private fun BintrayPackage.canAggregateToGroup(): Boolean {
        return githubRepository != null || vcsUrl != null || websiteUrl != null
    }

    private fun Pair<Artifact, BintrayPackage>.toLibraryGroup(libraries: List<Library>, licenses: List<BintrayLicense>): LibraryGroup {
        return createLibraryGroup(listOf(first.artifact), libraries, licenses, second)
    }

    private fun Map.Entry<BintrayPackage, List<Pair<Artifact, BintrayPackage>>>.toLibraryGroup(
        libraries: List<Library>,
        licenses: List<BintrayLicense>
    ): LibraryGroup {
        return createLibraryGroup(value.map { it.first.artifact }, libraries, licenses, key)
    }

    private fun createLibraryGroup(
        artifacts: List<String>,
        libraries: List<Library>,
        licenses: List<BintrayLicense>,
        bintrayPackage: BintrayPackage
    ): LibraryGroup {
        val foundLibraryAuthor = libraries.firstOrNull { it.author != Placeholder.author }?.author
        val foundLibraryUrl = libraries.firstOrNull { it.url != Placeholder.url }?.url
        val foundLibraryLicenses = libraries.firstOrNull {
            it.licenses.isNotEmpty() && it.licenses[0].name != Placeholder.licenseName && it.licenses[0].url != Placeholder.licenseUrl
        }
            ?.licenses

        return LibraryGroup(
            artifacts = artifacts,
            name = when {
                bintrayPackage.githubRepository != null -> bintrayPackage.githubRepository.replace("/", "_")
                bintrayPackage.repository != "jcenter" -> bintrayPackage.repository
                else -> Placeholder.name
            },
            author = foundLibraryAuthor ?: bintrayPackage.owner,
            description = bintrayPackage.description,
            url = when {
                foundLibraryUrl != null -> foundLibraryUrl
                bintrayPackage.githubRepository != null -> "https://github.com/${bintrayPackage.githubRepository}"
                bintrayPackage.websiteUrl != null -> bintrayPackage.websiteUrl
                bintrayPackage.vcsUrl != null -> bintrayPackage.vcsUrl
                else -> Placeholder.url
            },
            licenses = foundLibraryLicenses ?: bintrayPackage.licenses.map { x -> licenses.first { y -> y.name == x }.toLicense() }
        )
    }

    private fun BintrayLicense.toLicense(): License {
        return License(fullName, url)
    }
}