package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.graphics.Bitmap
import android.graphics.Color
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
import kotlinx.android.synthetic.main.activity_work_sheet_detail.*
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


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
        /*val b = Bitmap.createBitmap(100, 100,
                Bitmap.Config.ARGB_8888)
        b.eraseColor(Color.RED)
        val bitstring=bitmapToString(b)
        Log.d("bitmap","$bitstring")
        val mutableBitmap =stringToBitmap(bitstring).copy(Bitmap.Config.ARGB_8888,true)
        signature_view_detail.setBitmap(mutableBitmap)*/


    }

    override fun onMapReady(googleMap: GoogleMap) {
        val workSheetData = intent.getParcelableExtra<WorkSheet>(EXTRA_WORKSHEET)
        mMap = googleMap
        val location = LatLng(workSheetData.lat, workSheetData.lng)
        mMap.addMarker(MarkerOptions().position(location).title("Marker in ${workSheetData.customer}"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14.0f))
    }

    private fun bitmapToString(bitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        return Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT)
    }

    private fun stringToBitmap(cadena: String): Bitmap {
        val bytes = Base64.decode(cadena, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
