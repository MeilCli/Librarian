package net.meilcli.librarian.plugin.internal.bintray

import net.meilcli.librarian.plugin.entities.BintrayRepository
import net.meilcli.librarian.plugin.internal.ILoader
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

class BintrayRepositoriesLoader(
    private val project: Project
) : ILoader<List<BintrayRepository>> {

    private val bintrayDlRegex = Regex("(https://dl\\.bintray\\.com/([a-zA-Z0-9_\\-]+)/([a-zA-Z0-9_\\-]+))/?")

    override fun load(): List<BintrayRepository> {
        val result = mutableListOf<BintrayRepository>()

        for (repository in project.repositories) {
            if (repository !is MavenArtifactRepository) {
                continue
            }
            if (repository.url.toString().startsWith("https://jcenter.bintray.com")) {
                result += BintrayRepository(url = "https://jcenter.bintray.com", repository = "jcenter", owner = "bintray")
                continue
            }

            val match = bintrayDlRegex.matchEntire(repository.url.toString()) ?: continue
            val matchedGroups = match.groupValues
            if (matchedGroups.size < 4) {
                continue
            }

            val bintrayUrl = matchedGroups[1]
            val bintrayRepository = matchedGroups[3]
            val bintrayOwner = matchedGroups[2]
            result += BintrayRepository(url = bintrayUrl, repository = bintrayRepository, owner = bintrayOwner)
        }

        return result
    }
}