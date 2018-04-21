package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet
import com.dsdm.miw.uniovi.worksheets.server.APIService
import com.dsdm.miw.uniovi.worksheets.server.WorkSheetServer
import kotlinx.android.synthetic.main.activity_signature.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
class SignatureActivity : AppCompatActivity() {

    var api: APIService? = null

    companion object {
        const val EXTRA_WORKER = "SignatureActivity::date"
        const val EXTRA_CUSTOMER = "SignatureActivity::customer"
        const val EXTRA_START = "SignatureActivity::start"
        const val EXTRA_END = "SignatureActivity::end"
        const val EXTRA_DESCRIPTION = "SignatureActivity::description"
        const val EXTRA_LAT = "SignatureActivity::lat"
        const val EXTRA_LNG = "SignatureActivity::lng"

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
        Log.d("TAG","${intent.getDoubleExtra(EXTRA_LAT,0.0)}")
        Log.d("TAG","${intent.getDoubleExtra(EXTRA_LNG,0.0)}")
        signature_view.clearCanvas()

    }

    private fun confirm() {
        if (!signature_view.isBitmapEmpty) {

            api!!.addWorkSheet(intent.getStringExtra(EXTRA_WORKER),
                    intent.getStringExtra(EXTRA_CUSTOMER),
                    Date(intent.getLongExtra(EXTRA_START, 0)),
                    Date(intent.getLongExtra(EXTRA_END, 0)),
                    intent.getStringExtra(EXTRA_DESCRIPTION),
                    true, intent.getDoubleExtra(EXTRA_LAT,0.0),  intent.getDoubleExtra(EXTRA_LNG,0.0))
                    .enqueue(object : Callback<WorkSheet> {
                        override fun onResponse(call: Call<WorkSheet>, response: Response<WorkSheet>) {
                            if (response.isSuccessful()) {
                                //Redirect to main page
                            }
                        }

                        override fun onFailure(call: Call<WorkSheet>, t: Throwable) {

                        }
                    })
        }
    }
}
