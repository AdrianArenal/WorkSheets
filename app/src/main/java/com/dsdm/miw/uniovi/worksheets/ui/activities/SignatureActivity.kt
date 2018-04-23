package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.server.APIService
import com.dsdm.miw.uniovi.worksheets.server.WorkSheetServer
import com.dsdm.miw.uniovi.worksheets.util.GeneratePDFDocument
import kotlinx.android.synthetic.main.activity_signature.*
import okhttp3.ResponseBody
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.dsdm.miw.uniovi.worksheets.model.Autenticado
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.ByteArrayOutputStream
import java.util.Date


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
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
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
        if (checkEmptyFields()) {
            val worker = intent.getStringExtra(EXTRA_WORKER)
            val customer = intent.getStringExtra(EXTRA_CUSTOMER)
            val start = intent.getLongExtra(EXTRA_START, 0)
            val end = intent.getLongExtra(EXTRA_END, 0)
            val description = intent.getStringExtra(EXTRA_DESCRIPTION)
            val lat = intent.getDoubleExtra(EXTRA_LAT, 0.0)
            val long = intent.getDoubleExtra(EXTRA_LNG, 0.0)
            val sign = bitmapToString(signature_view.signatureBitmap)
            val password = editTextPasswordConfirm.text.toString()

            api!!.autenthicate(worker, password)
                    .enqueue(object : Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            if (response.isSuccessful) {
                                val resp = Gson().fromJson(response.body(), Autenticado::class.java)
                                if (resp.autenticado) {
                                    api!!.addWorkSheet(worker, customer, start, end, description, sign, lat, long)
                                            .enqueue(object : Callback<ResponseBody> {
                                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                                    if (response.isSuccessful) {
                                                        val fileName = "$dest/$customer-$start.pdf"
                                                        generatePDF(fileName)
                                                        alert(getString(R.string.send_email)) {
                                                            title = getString(R.string.send)
                                                            yesButton {
                                                                sendEmail(fileName, customer)
                                                            }
                                                            noButton { startActivity<MainActivity>() }
                                                        }.show()
                                                    }
                                                }

                                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                                    toast(R.string.errorUnexpected)
                                                }
                                            })
                                } else {
                                    toast(R.string.wrongPassword)
                                }
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            toast(R.string.errorUnexpected)
                        }
                    })
        }

    }

    private fun generatePDF(dest: String) {
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
            val contactPerson = editTextContactPerson.text.toString()
            GeneratePDFDocument().createPdf(dest, customer, worker, start, end,
                    desc, signature_view.signatureBitmap, contactPerson)
        }
    }

    private fun sendEmail(fileName: String, customer: String) {
        doAsync {
            val server = WorkSheetServer()
            val customer = server.getCustomerByName(customer)
            if (customer != null) {
                uiThread {
                    val fileLocation = File(fileName)
                    val path = Uri.fromFile(fileLocation)
                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.type = "vnd.android.cursor.dir/email"
                    val to = arrayOf(customer.email)
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
                    emailIntent.putExtra(Intent.EXTRA_STREAM, path)
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sheet))
                    startActivityForResult(Intent.createChooser(emailIntent, getString(R.string.send)), 1)
                }
            }
        }

    }

    private fun checkEmptyFields(): Boolean {
        if (editTextContactPerson.text.isEmpty() || editTextPasswordConfirm.text.isEmpty()) {
            toast(R.string.empty)
            return false
        } else if (signature_view.isBitmapEmpty) {
            toast(R.string.noSignature)
            return false
        }
        return true
    }

    private fun bitmapToString(bitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        return Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                toast(R.string.messageSent)
                startActivity<MainActivity>()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                toast(R.string.messageCancel)
                startActivity<MainActivity>()
            }
        }
    }


}
