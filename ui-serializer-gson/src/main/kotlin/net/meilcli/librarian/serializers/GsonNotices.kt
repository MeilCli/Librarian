package net.meilcli.librarian.serializers

import com.google.gson.annotations.SerializedName

data class GsonNotices(

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("notices")
    val notices: List<GsonNotice>?
) {

    fun isValid(): Boolean {
        return title != null && notices != null
    }

    fun toNotices(): Notices {
        return Notices(
            title = checkNotNull(title),
            description = description,
            notices = checkNotNull(notices).filter { it.isValid() }.map { it.toNotice() }
        )
    }
}