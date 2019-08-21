package com.ifansdev.umorili.api.dataclass

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class SourceOfQuotes(
        @SerializedName("site") val site: String,
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String,
        @SerializedName("parsel") val parsel: String,
        @SerializedName("encoding") val encoding: String,
        @SerializedName("linkpar") val linkpar: String,
        @SerializedName("desc") val desc: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(site)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(parsel)
        parcel.writeString(encoding)
        parcel.writeString(linkpar)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SourceOfQuotes> {
        override fun createFromParcel(parcel: Parcel): SourceOfQuotes {
            return SourceOfQuotes(parcel)
        }

        override fun newArray(size: Int): Array<SourceOfQuotes?> {
            return arrayOfNulls(size)
        }
    }
}
