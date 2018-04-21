package com.dsdm.miw.uniovi.worksheets.server

import com.dsdm.miw.uniovi.worksheets.model.Customer
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet
import com.dsdm.miw.uniovi.worksheets.model.Worker
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface APIService {

    @POST("worksheet")
    @FormUrlEncoded
    fun addWorkSheet(@Field("worker") worker: String,
                     @Field("customer") customer: String,
                     @Field("startDate") startDate : Date,
                     @Field("endDate") endDate: Date,
                     @Field("description") description: String,
                     @Field("signed") signed : Boolean,
                     @Field("lat") lat : Double,
                     @Field("lng") long : Double): Call<WorkSheet>
}