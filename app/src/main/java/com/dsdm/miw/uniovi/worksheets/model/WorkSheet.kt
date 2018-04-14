package com.dsdm.miw.uniovi.worksheets.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Model class WorkSheet. It stores the work sheets information
 */
data class WorkSheet(val customer: String, val worker : String, val startDate : Date,
                     val endDate: Date, val description: String, val signed : Boolean,
                     val lat : Double, val long : Double) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            TODO("startDate"),
            TODO("endDate"),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readDouble(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(customer)
        parcel.writeString(worker)
        parcel.writeString(description)
        parcel.writeByte(if (signed) 1 else 0)
        parcel.writeDouble(lat)
        parcel.writeDouble(long)
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