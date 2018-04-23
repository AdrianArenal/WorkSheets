package com.dsdm.miw.uniovi.worksheets.ui.filters

import android.widget.Filter
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet
import com.dsdm.miw.uniovi.worksheets.ui.adapters.WorkSheetListAdapter
import java.util.*

class WorkSheetsFilter(private var filterList: Array<WorkSheet>, private var adapter: WorkSheetListAdapter) : Filter() {

    //FILTERING OCURS
    override fun performFiltering(constraint: CharSequence?): FilterResults {
        @Suppress("NAME_SHADOWING")
        var constraint = constraint
        val results = FilterResults()
        //CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.isNotEmpty()) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase()
            //STORE OUR FILTERED PLAYERS
            val filteredPlayers = mutableListOf<WorkSheet>()
            for (i in 0 until filterList.size) {
                //CHECK

                if (filterList[i].customer.toUpperCase().contains(constraint)) {
                    filteredPlayers.add(filterList[i])
                }
            }

            results.count = filteredPlayers.size
            results.values = filteredPlayers
        } else {
            val filtered =filterList.toCollection(ArrayList())
            results.count = filtered.size
            results.values = filtered

        }
        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {

        val arraylist = results.values as ArrayList<WorkSheet>
        adapter.items = arraylist.toTypedArray()
        //REFRESH
        adapter.notifyDataSetChanged()

    }
}