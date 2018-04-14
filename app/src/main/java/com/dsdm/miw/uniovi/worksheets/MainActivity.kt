package com.dsdm.miw.uniovi.worksheets

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeComponents()
    }

    fun initializeComponents(){
        btNewWSheet.setOnClickListener{ createNewWorkSheet() }
    }

    private fun createNewWorkSheet(){
        startActivity<NewWorkSheetActivity>()
    }
}
