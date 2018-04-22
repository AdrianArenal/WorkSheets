package com.dsdm.miw.uniovi.worksheets.ui.adapters

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dsdm.miw.uniovi.worksheets.R
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet
import kotlinx.android.synthetic.main.item_work_sheet.view.*
import java.text.SimpleDateFormat
import java.util.*

class WorkSheetListAdapter(val items: Array<WorkSheet>, val itemClick: (WorkSheet) -> Unit)
    : RecyclerView.Adapter<WorkSheetListAdapter.ViewHolder>() {

    // Implementación de RecyclerView.ViewHolder que se usará en la función onCreateViewHolder
    class ViewHolder(val cardView: CardView, val itemClick: (WorkSheet) -> Unit)
        : RecyclerView.ViewHolder(cardView) {

        // Hace cambios en la vista creada según el elemento pasado.
        fun bindWorkSheet(worksheet: WorkSheet) {
            // Dentro del bloque with, this es worksheet
            // itemView es la vista item_work_sheet cargada más adelante
            with(worksheet) {
                itemView.txCustomer.text = customer
                itemView.txWorker.text = worker
                itemView.txDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(startDate)
                itemView.txDuration.text = ((endDate-startDate)/1000/60).toString() + " min"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    // Crea nuevas vistas para cada elemento de la lista. Lo invoca el LayoutManager.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Crea una nueva vista.
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_work_sheet, parent, false) as CardView
        // Retorna la vista bajo un objeto que herede de RecyclerView.ViewHolder
        return ViewHolder(view, itemClick)
    }

    // Devuelve el número de elementos.
    override fun getItemCount() = items.size

    // Llama a la función para rellenar la vista pasando el elemento de la posición dada.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindWorkSheet(items[position])
    }
}