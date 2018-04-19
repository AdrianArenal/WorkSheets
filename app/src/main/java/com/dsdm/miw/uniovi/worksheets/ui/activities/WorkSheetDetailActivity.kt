package com.dsdm.miw.uniovi.worksheets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet

class WorkSheetDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_WORKSHEET = "WorkSheetDetailActivity::worksheet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_sheet_detail)
        initialize()
    }

    private fun initialize(){
         Log.d("TAG",intent.getParcelableExtra<WorkSheet>(EXTRA_WORKSHEET).customer)
    }
}
