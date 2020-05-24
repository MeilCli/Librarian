package net.meilcli.librarian.plugin.internal.bintray

import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.BintrayPackage
import net.meilcli.librarian.plugin.entities.BintrayRepository
import net.meilcli.librarian.plugin.internal.IParameterizedLoader
import org.gradle.api.Project
import org.slf4j.LoggerFactory

class ArtifactAndBintrayRepositoryToBintrayPackageLoader(
    private val project: Project
) : IParameterizedLoader<Pair<Artifact, BintrayRepository>, BintrayPackage?> {

    private val api = IBintrayApi.default
    private val logger = LoggerFactory.getLogger(ArtifactAndBintrayRepositoryToBintrayPackageLoader::class.java)
    private val resultCache = mutableMapOf<Pair<Artifact, BintrayRepository>, BintrayPackage?>()
    private var notService = false

    override fun load(parameter: Pair<Artifact, BintrayRepository>): BintrayPackage? {
        if (notService) {
            project.logger.quiet("not service hit")
            return null
        }
        if (resultCache.containsKey(parameter)) {
            project.logger.quiet("cache hit")
            return resultCache[parameter]
        }

        try {
            // search package name, because name convention is difference each repository
            val searchResponse = api
                .searchPackage(
                    group = parameter.first.group,
                    artifact = parameter.first.name,
                    owner = parameter.second.owner,
                    repository = parameter.second.repository
                )
                .execute()

            if (500 <= searchResponse.code()) {
                notService = true
                return null
            }
            if (searchResponse.isSuccessful.not()) {
                resultCache[parameter] = null
                return null
            }

            val searchResults = searchResponse.body()
            if (searchResults == null) {
                resultCache[parameter] = null
                return null
            }
            val searchResult = searchResults.find {
                it.owner == parameter.second.owner && it.repository == parameter.second.repository
            }
            if (searchResult == null) {
                resultCache[parameter] = null
                return null
            }

            val packageResponse = api
                .getPackage(
                    owner = parameter.second.owner,
                    repository = parameter.second.repository,
                    packageName = searchResult.packageName
                )
                .execute()

            if (500 <= packageResponse.code()) {
                notService = true
                return null
            }
            if (packageResponse.isSuccessful.not()) {
                resultCache[parameter] = null
                return null
            }

            resultCache[parameter] = packageResponse.body()
            return resultCache[parameter]
        } catch (exception: Exception) {
            logger.warn("Librarian unknown exception", exception)
            project.logger.quiet("exception")
            return null
        }
    }
}