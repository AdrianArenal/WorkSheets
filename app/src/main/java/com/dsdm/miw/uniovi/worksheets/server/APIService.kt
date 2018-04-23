package com.dsdm.miw.uniovi.worksheets.server

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface APIService {

    @POST("worksheet")
    @FormUrlEncoded
    fun addWorkSheet(@Field("worker") worker: String,
                     @Field("customer") customer: String,
                     @Field("startDate") startDate : Long,
                     @Field("endDate") endDate: Long,
                     @Field("description") description: String,
                     @Field("sign") sign : String,
                     @Field("lat") lat : Double,
                     @Field("lng") long : Double): Call<ResponseBody>

    @POST("customer")
    @FormUrlEncoded
    fun addCustomer(@Field("businessName") businessName: String,
                     @Field("address") address: String,
                     @Field("contactPerson") contactPerson: String,
                     @Field("email") email : String,
                     @Field("phoneNumber") phoneNumber : Int): Call<ResponseBody>

    @POST("/worker/autenticar/")
    @FormUrlEncoded
    fun autenthicate(@Field("name") name: String,
                    @Field("password") password: String): Call<JsonObject>
}