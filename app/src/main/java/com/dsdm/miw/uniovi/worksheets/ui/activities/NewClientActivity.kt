package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.server.APIService
import com.dsdm.miw.uniovi.worksheets.server.WorkSheetServer
import kotlinx.android.synthetic.main.activity_new_client.*
import okhttp3.ResponseBody
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewClientActivity : AppCompatActivity() {
    private var api: APIService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)
        setSupportActionBar(toolbarNewClient)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        api = WorkSheetServer.api
        initialize()
    }
    private fun initialize(){
        btAddCustomer.setOnClickListener { addClient() }
    }

    private fun addClient(){
        val businessName = editTextBusinessName.text.toString()
        val address = editTextAddress.text.toString()
        val contactPerson = editTextContactPerson.text.toString()
        val email = editTextEmail.text.toString()
        val phoneNumber = editTextPhone.text.toString().toInt()
        api!!.addCustomer(businessName = businessName,address = address,contactPerson = contactPerson,
                email = email,phoneNumber = phoneNumber)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            toast("Cliente añadido")
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
                })
    }
}
