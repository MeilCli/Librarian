package net.meilcli.librarian.serializers

import com.google.gson.annotations.SerializedName

data class GsonNotice(

    @SerializedName("artifacts")
    val artifacts: List<String>?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("author")
    val author: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("licenses")
    val licenses: List<GsonLicense>?
) {

    fun isValid(): Boolean {
        return artifacts != null && name != null && author != null && url != null && licenses != null && licenses.all { it.isValid() }
    }

    fun toNotice(): Notice {
        return Notice(
            artifacts = checkNotNull(artifacts),
            name = checkNotNull(name),
            author = checkNotNull(author),
            url = checkNotNull(url),
            description = description,
            licenses = checkNotNull(licenses).map { it.toLicense() }
        )
    }
}