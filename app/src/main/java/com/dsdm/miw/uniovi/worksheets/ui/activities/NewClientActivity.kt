package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dsdm.miw.uniovi.worksheets.R
import kotlinx.android.synthetic.main.activity_new_client.*


class NewClientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)
        setSupportActionBar(toolbarNewClient)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initialize()
    }
    private fun initialize(){
        btAddCustomer.setOnClickListener { addClient() }
    }

    private fun addClient(){

    }
}
