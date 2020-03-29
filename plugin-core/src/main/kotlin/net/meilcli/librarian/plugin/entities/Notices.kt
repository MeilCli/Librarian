package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notices(

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String?,

    @SerialName("notices")
    val notices: List<Notice>
)