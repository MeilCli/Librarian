package net.meilcli.librarian

import android.os.Parcel
import android.os.Parcelable

class ParcelableLicense : ILicense, Parcelable {

    override val name: String
    override val url: String

    constructor(license: ILicense) {
        this.name = license.name
        this.url = license.url
    }

    constructor(parcel: Parcel) {
        this.name = checkNotNull(parcel.readString())
        this.url = checkNotNull(parcel.readString())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelableLicense> {

        override fun createFromParcel(parcel: Parcel): ParcelableLicense {
            return ParcelableLicense(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableLicense?> {
            return arrayOfNulls(size)
        }
    }
}