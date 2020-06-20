package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoticeResource(

    @SerialName("artifacts")
    val artifacts: List<String>,

    @SerialName("licenses")
    val licenses: List<License>
)