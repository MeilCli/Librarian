package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BintraySearchResult(
    @SerialName("name")
    val packageName: String,

    @SerialName("owner")
    val owner: String,

    @SerialName("repo")
    val repository: String
) {

    fun urlEscapedPackageName() = packageName.replace(":", "&3A")
}