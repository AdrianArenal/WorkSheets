package com.dsdm.miw.uniovi.worksheets

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_work_sheet.*
import java.text.SimpleDateFormat
import java.util.*

class NewWorkSheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_work_sheet)
        initializeComponents()
    }

    fun initializeComponents(){
        etDate.setText(SimpleDateFormat("dd/MM/yyyy").format(Date()))
        //Load workers names
        //Load client names
    }
}
