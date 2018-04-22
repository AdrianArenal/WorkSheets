package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet
import com.dsdm.miw.uniovi.worksheets.server.APIService
import com.dsdm.miw.uniovi.worksheets.server.WorkSheetServer
import com.dsdm.miw.uniovi.worksheets.util.GeneratePDFDocument
import kotlinx.android.synthetic.main.activity_signature.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class SignatureActivity : AppCompatActivity() {

    private var api: APIService? = null

    companion object {
        const val EXTRA_WORKER = "SignatureActivity::date"
        const val EXTRA_CUSTOMER = "SignatureActivity::customer"
        const val EXTRA_START = "SignatureActivity::start"
        const val EXTRA_END = "SignatureActivity::end"
        const val EXTRA_DESCRIPTION = "SignatureActivity::description"
        const val EXTRA_LAT = "SignatureActivity::lat"
        const val EXTRA_LNG = "SignatureActivity::lng"
        val dest = Environment.getExternalStorageDirectory().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signature)
        setSupportActionBar(toolbarSignature)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        api = WorkSheetServer.api
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
        if (!signature_view.isBitmapEmpty) {
            api!!.addWorkSheet(intent.getStringExtra(EXTRA_WORKER),
                    intent.getStringExtra(EXTRA_CUSTOMER),
                    Date(intent.getLongExtra(EXTRA_START, 0)),
                    Date(intent.getLongExtra(EXTRA_END, 0)),
                    intent.getStringExtra(EXTRA_DESCRIPTION),
                    true, intent.getDoubleExtra(EXTRA_LAT,0.0),
                    intent.getDoubleExtra(EXTRA_LNG,0.0))
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                val customer = intent.getStringExtra(EXTRA_CUSTOMER)
                                val timestamp = intent.getLongExtra(EXTRA_START, 0)
                                generatePDF("$dest/$customer-$timestamp.pdf")
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
                    })
        }
    }

    private fun generatePDF(dest : String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1)
        } else {
            val file = File(dest)
            file.parentFile.mkdirs()
            val customer = intent.getStringExtra(EXTRA_CUSTOMER)
            val worker = intent.getStringExtra(EXTRA_WORKER)
            val start = Date(intent.getLongExtra(EXTRA_START, 0))
            val end = Date(intent.getLongExtra(EXTRA_END, 0))
            val desc = intent.getStringExtra(EXTRA_DESCRIPTION)
            GeneratePDFDocument().createPdf(dest, customer, worker, start, end,
                    desc, signature_view.signatureBitmap)
        }
    }

    /**private fun sendEmail(filename: String) {
        val filelocation = File(dest, filename)
        val path = Uri.fromFile(filelocation)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "vnd.android.cursor.dir/email"
        val to = arrayOf("dariorginf@gmail.com")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        emailIntent.putExtra(Intent.EXTRA_STREAM, path)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }*/
}
