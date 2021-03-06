package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notice(

    @SerialName("name")
    val name: String,

    @SerialName("author")
    val author: String,

    @SerialName("url")
    val url: String,

    @SerialName("description")
    val description: String?,

    @SerialName("resources")
    val resources: List<NoticeResource>
)