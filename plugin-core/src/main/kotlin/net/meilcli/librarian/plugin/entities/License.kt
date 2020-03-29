package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class License(

    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String
)