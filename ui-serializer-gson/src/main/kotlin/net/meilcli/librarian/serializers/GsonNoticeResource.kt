package net.meilcli.librarian.serializers

import com.google.gson.annotations.SerializedName

data class GsonNoticeResource(

    @SerializedName("artifacts")
    val artifacts: List<String>?,

    @SerializedName("licenses")
    val licenses: List<GsonLicense>?
) {

    fun isValid(): Boolean {
        return artifacts != null && licenses != null && licenses.all { it.isValid() }
    }

    fun toNoticeResource(): NoticeResource {
        return NoticeResource(
            artifacts = checkNotNull(artifacts),
            licenses = checkNotNull(licenses).map { it.toLicense() }
        )
    }
}