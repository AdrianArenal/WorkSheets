package com.dsdm.miw.uniovi.worksheets.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Model class Worker. It contains application users data
 */
data class Worker(val name : String, val password : String,
                  val email : String, val phoneNumber: Long) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(password)
        parcel.writeString(email)
        parcel.writeLong(phoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Worker> {
        override fun createFromParcel(parcel: Parcel): Worker {
            return Worker(parcel)
        }

        override fun newArray(size: Int): Array<Worker?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "${name} - ${password}"
    }
}


class Autenticado(var autenticado: Boolean, var token: String){}