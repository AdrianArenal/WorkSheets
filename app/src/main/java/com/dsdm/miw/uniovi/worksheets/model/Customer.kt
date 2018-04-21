package com.dsdm.miw.uniovi.worksheets.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Model class Customer. It contains application customers data
 */
data class Customer(val businessName : String, val address : String, val contactPerson : String,
                    val email : String, val phoneNumber: Long) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(businessName)
        parcel.writeString(address)
        parcel.writeString(contactPerson)
        parcel.writeString(email)
        parcel.writeLong(phoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun createFromParcel(parcel: Parcel): Customer {
            return Customer(parcel)
        }

        override fun newArray(size: Int): Array<Customer?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "${businessName} - ${address}"
    }
}