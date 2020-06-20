package net.meilcli.librarian.serializers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.meilcli.librarian.INoticeResource

@Serializable
data class NoticeResource(

    @SerialName("artifacts")
    override val artifacts: List<String>,

    @SerialName("licenses")
    override val licenses: List<License>
) : INoticeResource