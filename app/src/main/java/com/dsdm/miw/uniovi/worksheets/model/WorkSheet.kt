package com.dsdm.miw.uniovi.worksheets.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Model class WorkSheet. It stores the work sheets information
 */
data class WorkSheet(val customer: String, val worker: String, val startDate: Date,
                     val endDate: Date, val description: String, val sign: String,
                     val lat: Double, val lng: Double) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            Date(parcel.readLong()),
            Date(parcel.readLong()),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(customer)
        parcel.writeString(worker)
        parcel.writeLong(startDate.time)
        parcel.writeLong(endDate.time)
        parcel.writeString(description)
        parcel.writeString(sign)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorkSheet> {
        override fun createFromParcel(parcel: Parcel): WorkSheet {
            return WorkSheet(parcel)
        }

        override fun newArray(size: Int): Array<WorkSheet?> {
            return arrayOfNulls(size)
        }
    }
}