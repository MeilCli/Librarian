package net.meilcli.librarian.serializer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.meilcli.librarian.INotice
import net.meilcli.librarian.INotices

@Serializable
data class Notices(

    @SerialName("title")
    override val title: String,

    @SerialName("description")
    override val description: String?,

    @SerialName("notices")
    override val notices: List<INotice>
) : INotices