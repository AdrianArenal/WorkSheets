package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.model.Customer
import com.dsdm.miw.uniovi.worksheets.server.WorkSheetServer
import kotlinx.android.synthetic.main.activity_new_work_sheet.*
import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.util.*

private const val REQUEST_FINE_LOCATION_PERMISSIONS = 1
class NewWorkSheetActivity : AppCompatActivity() {

    private var locationManager: LocationManager? = null
    private var locLat = 0.0
    private var locLng = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_work_sheet)
        initializeComponents()
        checkLocationPermission()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeComponents() {
        etDate.setText(SimpleDateFormat("dd/MM/yyyy").format(Date()))
        btSign.setOnClickListener{ createSign() }
        doAsync {
            val server = WorkSheetServer()
            val workers = server.getWorkers()
            val customers = server.getCustomers()
            if (workers != null && customers != null) {
                activityUiThreadWithContext {
                    spWorkers.adapter =
                            ArrayAdapter(this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    workers.map { it.name }.toMutableList())
                    spCustomers.adapter =
                            ArrayAdapter(this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    customers.map { it.businessName }.toMutableList())

                }
            }
        }
    }

    private fun createSign(){
        if(checkEmptyFields() && checkTimes())
            startActivity<SignatureActivity>(
                    SignatureActivity.EXTRA_WORKER to spWorkers.selectedItem.toString(),
                    SignatureActivity.EXTRA_CUSTOMER to spCustomers.selectedItem.toString(),
                    SignatureActivity.EXTRA_START to convertHourToLong(etStart.text.toString()),
                    SignatureActivity.EXTRA_END to convertHourToLong(etEnd.text.toString()),
                    SignatureActivity.EXTRA_DESCRIPTION to etDescription.text.toString(),
                    SignatureActivity.EXTRA_LAT to locLat,
                    SignatureActivity.EXTRA_LNG to locLng
            )
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Se entra si el usuario no aceptó el permiso anteriormente.
                // Mostrar información al usuario de porqué es necesario el permiso.
            }
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_FINE_LOCATION_PERMISSIONS)
        } else {
            getLocation()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_FINE_LOCATION_PERMISSIONS -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // El permiso se ha dado y se puede hacer la tarea oportuna con el calendario.
                    getLocation()
                } else {
                    // El permiso no se ha dado. Deshabilitamos la funcionalidad que dependa de él.
                }
            }
        }
    }

    private fun getLocation() : Location?{
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
        } catch (ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }
        return null
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locLat=location.latitude
            locLng=location.longitude
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun checkEmptyFields() : Boolean{
        if(etDate.text.isEmpty()
                || etStart.text.isEmpty()
                || etEnd.text.isEmpty()
                || etDescription.text.isEmpty()){
            toast(R.string.empty)
            return false
        }
        return true
    }

    private fun checkTimes() : Boolean{
        if((convertHourToLong(etEnd.text.toString()) -
                convertHourToLong(etStart.text.toString()) < 0)
                || convertHourToLong(etStart.text.toString()) == -1L
                || convertHourToLong(etEnd.text.toString()) == -1L){
            longToast(R.string.check_hour)
            return false
        }
        return true
    }

    private fun convertHourToLong(hour: String): Long{
        val hourSplit = hour.split(":")
        if(hourSplit.count() == 0 || hourSplit[0].toInt() > 23 || hourSplit[1].toInt() > 59)
            return -1
        val date: String = etDate.text.toString()
        return SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
                    .parse("${date} ${hour}").time
    }

}
