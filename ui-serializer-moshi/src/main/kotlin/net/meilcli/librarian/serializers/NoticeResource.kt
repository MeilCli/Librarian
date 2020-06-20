package net.meilcli.librarian.serializers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import net.meilcli.librarian.INoticeResource

@JsonClass(generateAdapter = true)
data class NoticeResource(

    @Json(name = "artifacts")
    override val artifacts: List<String>,

    @Json(name = "licenses")
    override val licenses: List<License>
) : INoticeResource