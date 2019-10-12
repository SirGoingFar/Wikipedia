package com.eemf.sirgoingfar.wikipedia.models

import android.os.Parcel
import android.os.Parcelable

class WikiResult {
    val query: WikiQueryData? = null

    class WikiQueryData {
        val pages: List<WikiPage> = ArrayList()
    }

    class WikiPage() : Parcelable {
        var pageid: Int? = null
        var title: String? = null
        var fullurl: String? = null
        var thumbnail: WikiThumbnail? = null

        constructor(parcel: Parcel) : this() {
            pageid = parcel.readValue(Int::class.java.classLoader) as? Int
            title = parcel.readString()
            fullurl = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(pageid)
            parcel.writeString(title)
            parcel.writeString(fullurl)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<WikiPage> {
            override fun createFromParcel(parcel: Parcel): WikiPage {
                return WikiPage(parcel)
            }

            override fun newArray(size: Int): Array<WikiPage?> {
                return arrayOfNulls(size)
            }
        }
    }

    class WikiThumbnail {
        val source: String? = null
        val width: Int? = 0
        val height: Int? = 0
    }

}