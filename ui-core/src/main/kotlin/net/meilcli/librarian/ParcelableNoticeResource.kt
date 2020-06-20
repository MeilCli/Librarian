package net.meilcli.librarian

import android.os.Parcel
import android.os.Parcelable

class ParcelableNoticeResource : INoticeResource, Parcelable {

    override val artifacts: List<String>
    override val licenses: List<ParcelableLicense>

    constructor(noticeResource: INoticeResource) {
        this.artifacts = noticeResource.artifacts
        this.licenses = noticeResource.licenses.map { ParcelableLicense(it) }
    }

    constructor(parcel: Parcel) {
        this.artifacts = checkNotNull(parcel.createStringArrayList())
        this.licenses = checkNotNull(parcel.createTypedArrayList(ParcelableLicense.CREATOR))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(artifacts)
        parcel.writeTypedList(licenses)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelableNoticeResource> {

        override fun createFromParcel(parcel: Parcel): ParcelableNoticeResource {
            return ParcelableNoticeResource(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableNoticeResource?> {
            return arrayOfNulls(size)
        }
    }
}