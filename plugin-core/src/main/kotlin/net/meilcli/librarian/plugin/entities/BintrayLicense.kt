package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BintrayLicense(
    @SerialName("name")
    val name: String,

    @SerialName("longname")
    val fullName: String,

    @SerialName("url")
    val url: String
)