package net.meilcli.librarian

import android.os.Parcel
import android.os.Parcelable

class ParcelableNotice : INotice, Parcelable {

    override val name: String
    override val author: String
    override val url: String
    override val description: String?
    override val resources: List<ParcelableNoticeResource>

    constructor(notice: INotice) {
        this.name = notice.name
        this.author = notice.author
        this.url = notice.url
        this.description = notice.description
        this.resources = notice.resources.map { ParcelableNoticeResource(it) }
    }

    constructor(parcel: Parcel) {
        this.name = checkNotNull(parcel.readString())
        this.author = checkNotNull(parcel.readString())
        this.url = checkNotNull(parcel.readString())
        this.description = parcel.readString()
        this.resources = checkNotNull(parcel.createTypedArrayList(ParcelableNoticeResource.CREATOR))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(author)
        parcel.writeString(url)
        parcel.writeString(description)
        parcel.writeTypedList(resources)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelableNotice> {

        override fun createFromParcel(parcel: Parcel): ParcelableNotice {
            return ParcelableNotice(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableNotice?> {
            return arrayOfNulls(size)
        }
    }
}