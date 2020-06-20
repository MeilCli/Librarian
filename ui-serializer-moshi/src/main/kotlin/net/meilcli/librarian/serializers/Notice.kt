package net.meilcli.librarian.serializers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import net.meilcli.librarian.INotice

@JsonClass(generateAdapter = true)
data class Notice(

    @Json(name = "name")
    override val name: String,

    @Json(name = "author")
    override val author: String,

    @Json(name = "url")
    override val url: String,

    @Json(name = "description")
    override val description: String?,

    @Json(name = "resources")
    override val resources: List<NoticeResource>
) : INotice