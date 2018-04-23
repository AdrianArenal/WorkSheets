package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.ui.adapters.WorkSheetListAdapter
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet
import com.dsdm.miw.uniovi.worksheets.server.WorkSheetServer
import kotlinx.android.synthetic.main.activity_list_work_sheets.*
import kotlinx.android.synthetic.main.activity_new_work_sheet.*
import org.jetbrains.anko.activityUiThreadWithContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import java.util.*

class ListWorkSheets : AppCompatActivity() {

    val myAdapter = WorkSheetListAdapter(arrayOf()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_work_sheets)
        setSupportActionBar(toolbarListWorkSheets)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initialize()
        searchViewInit()
    }

    private fun initialize() {
        doAsync {
            val server = WorkSheetServer()
            val worksheets = server.getWorkSheets()
            if (worksheets != null) {
                activityUiThreadWithContext {
                    myAdapter.items = worksheets
                    myAdapter.itemClick ={
                        startActivity<WorkSheetDetailActivity>(
                                WorkSheetDetailActivity.EXTRA_WORKSHEET to it)
                    }
                    worksheetList.layoutManager = LinearLayoutManager(this)
                  /*  worksheetList.adapter = WorkSheetListAdapter(worksheets) {
                        startActivity<WorkSheetDetailActivity>(
                                WorkSheetDetailActivity.EXTRA_WORKSHEET to it
                        )
                    }*/
                    worksheetList.adapter=myAdapter
                }
            }
        }
    }

    private fun searchViewInit() {
        mSearch.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                myAdapter.getFilter().filter(newText)
                return false
            }


            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })


    }
}
