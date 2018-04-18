package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dsdm.miw.uniovi.worksheets.R
import kotlinx.android.synthetic.main.activity_new_work_sheet.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class NewWorkSheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_work_sheet)
        initializeComponents()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun initializeComponents(){
        etDate.setText(SimpleDateFormat("dd/MM/yyyy").format(Date()))
        btSign.setOnClickListener{ createSign() }
        //Load workers names
        //Load client names
    }


    fun createSign(){
        /*val i = Intent(this, SignatureActivity::class.java)
        startActivity(i)*/
        startActivity<SignatureActivity>()
    }
}
