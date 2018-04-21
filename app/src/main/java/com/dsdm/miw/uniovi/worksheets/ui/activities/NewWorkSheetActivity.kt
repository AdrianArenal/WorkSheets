package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.server.WorkSheetServer
import kotlinx.android.synthetic.main.activity_new_work_sheet.*
import org.jetbrains.anko.activityUiThreadWithContext
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import org.jetbrains.anko.doAsync
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class NewWorkSheetActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_work_sheet)
        initializeComponents()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeComponents(){
        etDate.setText(SimpleDateFormat("dd/MM/yyyy").format(Date()))
        btSign.setOnClickListener{ createSign() }
        doAsync() {
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
        if(checkEmptyFields())
            startActivity<SignatureActivity>(
                    SignatureActivity.EXTRA_WORKER to spWorkers.selectedItem.toString(),
                    SignatureActivity.EXTRA_CUSTOMER to spCustomers.selectedItem.toString(),
                    SignatureActivity.EXTRA_START to convertHourToLong(etStart.text.toString()),
                    SignatureActivity.EXTRA_END to convertHourToLong(etEnd.text.toString()),
                    SignatureActivity.EXTRA_DESCRIPTION to etDescription.text.toString()
            )
    }

    private fun checkEmptyFields() : Boolean{
        if(etDate.text.isEmpty()
                || etStart.text.isEmpty()
                || etEnd.text.isEmpty()
                || etDescription.text.isEmpty()){
            Toast.makeText(this, getString(R.string.empty),
                    Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun convertHourToLong(hour: String): Long{
        val date: String = etDate.text.toString()
        return SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
                .parse("${date} ${hour}").time;
    }
}
