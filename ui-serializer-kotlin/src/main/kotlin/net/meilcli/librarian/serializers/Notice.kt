package net.meilcli.librarian.serializers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.meilcli.librarian.INotice

@Serializable
data class Notice(

    @SerialName("name")
    override val name: String,

    @SerialName("author")
    override val author: String,

    @SerialName("url")
    override val url: String,

    @SerialName("description")
    override val description: String?,

    @SerialName("resources")
    override val resources: List<NoticeResource>
) : INotice