package net.meilcli.librarian.serializers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import net.meilcli.librarian.ILicense

@JsonClass(generateAdapter = true)
data class License(

    @Json(name = "name")
    override val name: String,

    @Json(name = "url")
    override val url: String
) : ILicense