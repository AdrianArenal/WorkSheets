package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dsdm.miw.uniovi.worksheets.R
import kotlinx.android.synthetic.main.activity_signature.*

class SignatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signature)
        setSupportActionBar(toolbarSignature)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initialize()
    }

    private fun initialize() {
        btnClean.setOnClickListener { clean() }
        btnConfirm.setOnClickListener { confirm() }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun clean() {
        signature_view.clearCanvas()
    }

    private fun confirm() {
        Log.d("TAG", "Bitmap vacio ?  ${signature_view.isBitmapEmpty}")
        Log.d("TAG", "Confirmar signature ${signature_view.signatureBitmap.height}")

    }


}
