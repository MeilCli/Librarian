package net.meilcli.librarian

import android.os.Parcel
import android.os.Parcelable

class ParcelableNotices : INotices, Parcelable {

    override val title: String
    override val description: String?
    override val notices: List<ParcelableNotice>

    constructor(notices: INotices) {
        this.title = notices.title
        this.description = notices.description
        this.notices = notices.notices.map { ParcelableNotice(it) }
    }

    constructor(parcel: Parcel) {
        this.title = checkNotNull(parcel.readString())
        this.description = parcel.readString()
        this.notices = checkNotNull(parcel.createTypedArrayList(ParcelableNotice.CREATOR))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeTypedList(notices)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelableNotices> {

        override fun createFromParcel(parcel: Parcel): ParcelableNotices {
            return ParcelableNotices(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableNotices?> {
            return arrayOfNulls(size)
        }
    }
}