package com.ifansdev.umorili.api.dataclass

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Quote(
        @SerializedName("site") val site: String,
        @SerializedName("name") val name: String,
        @SerializedName("decs") val decs: String,
        @SerializedName("elementPureHtml") val htmlText: String,
        @SerializedName("link") val link: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(site)
        parcel.writeString(name)
        parcel.writeString(decs)
        parcel.writeString(htmlText)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quote> {
        override fun createFromParcel(parcel: Parcel): Quote {
            return Quote(parcel)
        }

        override fun newArray(size: Int): Array<Quote?> {
            return arrayOfNulls(size)
        }
    }
}