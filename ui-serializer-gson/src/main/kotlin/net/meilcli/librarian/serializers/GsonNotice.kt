package net.meilcli.librarian.serializers

import com.google.gson.annotations.SerializedName

data class GsonNotice(

    @SerializedName("name")
    val name: String?,

    @SerializedName("author")
    val author: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("resources")
    val resources: List<GsonNoticeResource>?
) {

    fun isValid(): Boolean {
        return name != null && author != null && url != null && resources != null && resources.all { it.isValid() }
    }

    fun toNotice(): Notice {
        return Notice(
            name = checkNotNull(name),
            author = checkNotNull(author),
            url = checkNotNull(url),
            description = description,
            resources = checkNotNull(resources).map { it.toNoticeResource() }
        )
    }
}