package net.meilcli.librarian.serializers

import com.google.gson.annotations.SerializedName

data class GsonLicense(
    @SerializedName("name")
    val name: String?,

    @SerializedName("url")
    val url: String?
) {

    fun isValid(): Boolean {
        return name != null && url != null
    }

    fun toLicense(): License {
        return License(
            checkNotNull(name),
            checkNotNull(url)
        )
    }
}