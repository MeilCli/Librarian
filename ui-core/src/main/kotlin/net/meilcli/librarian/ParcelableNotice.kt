package net.meilcli.librarian

import android.os.Parcel
import android.os.Parcelable

class ParcelableNotice : INotice, Parcelable {

    override val artifacts: List<String>
    override val name: String
    override val author: String
    override val url: String
    override val description: String?
    override val licenses: List<ParcelableLicense>

    constructor(notice: INotice) {
        this.artifacts = notice.artifacts
        this.name = notice.name
        this.author = notice.author
        this.url = notice.url
        this.description = notice.description
        this.licenses = notice.licenses.map { ParcelableLicense(it) }
    }

    constructor(parcel: Parcel) {
        this.artifacts = checkNotNull(parcel.createStringArrayList())
        this.name = checkNotNull(parcel.readString())
        this.author = checkNotNull(parcel.readString())
        this.url = checkNotNull(parcel.readString())
        this.description = parcel.readString()
        this.licenses = checkNotNull(parcel.createTypedArrayList(ParcelableLicense.CREATOR))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(artifacts)
        parcel.writeString(name)
        parcel.writeString(author)
        parcel.writeString(url)
        parcel.writeString(description)
        parcel.writeTypedList(licenses)
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