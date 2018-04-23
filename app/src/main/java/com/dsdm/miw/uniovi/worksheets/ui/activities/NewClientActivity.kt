package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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

    private fun initialize() {
        btAddCustomer.setOnClickListener { addClient() }
    }

    private fun addClient() {
        if (checkEmptyFields() && checkValidEmail()) {
            val businessName = editTextBusinessName.text.toString()
            val address = editTextAddress.text.toString()
            val contactPerson = editTextContactPerson.text.toString()
            val email = editTextEmail.text.toString()
            val phoneNumber = editTextPhone.text.toString().toInt()
            api!!.addCustomer(businessName = businessName, address = address, contactPerson = contactPerson,
                    email = email, phoneNumber = phoneNumber)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                toast(R.string.clientAdded)
                                startActivity<MainActivity>()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            toast(R.string.errorUnexpected)
                        }
                    })
        }


    }

    private fun checkEmptyFields(): Boolean {
        if (editTextBusinessName.text.isEmpty()
                || editTextAddress.text.isEmpty()
                || editTextEmail.text.isEmpty()
                || editTextPhone.text.isEmpty()
                || editTextContactPerson.text.isEmpty()) {
            toast(R.string.empty)

            return false
        }
        return true
    }
    private fun checkValidEmail():Boolean {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text).matches()){
            toast(R.string.invalidEmail)
            return false
        }
        return true
    }

}
