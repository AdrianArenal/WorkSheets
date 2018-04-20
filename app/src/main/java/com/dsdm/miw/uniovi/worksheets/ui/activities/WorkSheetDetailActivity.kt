package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class WorkSheetDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    companion object {
        const val EXTRA_WORKSHEET = "WorkSheetDetailActivity::worksheet"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_sheet_detail)

        initialize()
    }

    private fun initialize() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        Log.d("TAG", intent.getParcelableExtra<WorkSheet>(EXTRA_WORKSHEET).customer)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val workSheetData = intent.getParcelableExtra<WorkSheet>(EXTRA_WORKSHEET)
        mMap = googleMap
        val location = LatLng(workSheetData.lat, workSheetData.long)
        mMap.addMarker(MarkerOptions().position(location).title("Marker in Worksheet"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14.0f))
    }
}
