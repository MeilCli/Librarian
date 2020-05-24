package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BintrayPackage(
    @SerialName("repo")
    val repository: String,

    @SerialName("owner")
    val owner: String,

    @SerialName("desc")
    val description: String? = null,

    @SerialName("licenses")
    val licenses: List<String> = emptyList(),

    @SerialName("github_repo")
    val githubRepository: String? = null,

    @SerialName("vcs_url")
    val vcsUrl: String? = null,

    @SerialName("website_url")
    val websiteUrl: String? = null
)