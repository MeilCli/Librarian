package net.meilcli.librarian.plugin.internal.bintray

import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.BintrayRepository
import net.meilcli.librarian.plugin.internal.ILoader
import net.meilcli.librarian.plugin.internal.IParameterizedLoader
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory

class ArtifactToBintrayRepositoryLoader(
    bintrayRepositoriesLoader: ILoader<List<BintrayRepository>>
) : IParameterizedLoader<Artifact, BintrayRepository?> {

    private val client = OkHttpClient()
    private val logger = LoggerFactory.getLogger(ArtifactToBintrayRepositoryLoader::class.java)
    private val bintrayRepositories = bintrayRepositoriesLoader.load()
    private val notServiceRepositories = mutableListOf<BintrayRepository>()
    private val resultCache = mutableMapOf<Artifact, BintrayRepository?>()

    override fun load(parameter: Artifact): BintrayRepository? {
        if (resultCache.containsKey(parameter)) {
            return resultCache[parameter]
        }
        for (bintrayRepository in bintrayRepositories) {
            if (notServiceRepositories.contains(bintrayRepository)) {
                continue
            }

            val httpStatusCode = try {
                head(bintrayRepository, parameter)
            } catch (exception: Exception) {
                logger.warn("Librarian: unknown exception when head bintray", exception)
                400
            }
            if (500 <= httpStatusCode) {
                notServiceRepositories += bintrayRepository
                continue
            }
            if (300 <= httpStatusCode) {
                continue
            }
            resultCache[parameter] = bintrayRepository
            return bintrayRepository
        }

        return null
    }

    private fun head(bintrayRepository: BintrayRepository, artifact: Artifact): Int {
        fun String.replaceDot(): String {
            return replace('.', '/')
        }

        val request = Request.Builder()
            .head()
            .url("${bintrayRepository.url}/${artifact.group.replaceDot()}/${artifact.name}/${artifact.version}/${artifact.name}-${artifact.version}.pom")
            .build()

        client.newCall(request).execute().use {
            return it.code
        }
    }
}