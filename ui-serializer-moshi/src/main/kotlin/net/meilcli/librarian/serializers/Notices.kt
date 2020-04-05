package net.meilcli.librarian.serializers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import net.meilcli.librarian.INotices

@JsonClass(generateAdapter = true)
data class Notices(

    @Json(name = "title")
    override val title: String,

    @Json(name = "description")
    override val description: String?,

    @Json(name = "notices")
    override val notices: List<Notice>
) : INotices