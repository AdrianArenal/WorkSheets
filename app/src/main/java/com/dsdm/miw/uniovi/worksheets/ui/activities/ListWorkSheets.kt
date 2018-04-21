package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.ui.adapters.WorkSheetListAdapter
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet
import kotlinx.android.synthetic.main.activity_list_work_sheets.*
import org.jetbrains.anko.startActivity
import java.util.*

class ListWorkSheets : AppCompatActivity() {

    private val items = listOf(
            WorkSheet("Adrian","Dario", Date(),Date(),"Descripcion","",-34.0,151.0),
            WorkSheet("Pedro","Dario", Date(),Date(),"Descripcion","",40.416775,4.1),
            WorkSheet("Manolo","Dario", Date(),Date(),"Descripcion","",19.432608,-99.133209),
            WorkSheet("Pepito","Dario", Date(),Date(),"Descripcion","",3.2,4.1),
            WorkSheet("Julian","Dario", Date(),Date(),"Descripcion","",3.2,4.1)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_work_sheets)
        initialize()
    }

    private fun initialize(){
        worksheetList.layoutManager = LinearLayoutManager(this)
        worksheetList.adapter = WorkSheetListAdapter(items) {
            startActivity<WorkSheetDetailActivity>(
                    WorkSheetDetailActivity.EXTRA_WORKSHEET to it
            )
        }
    }
}
