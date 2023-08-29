package com.tyme.feature_dashboard.presentation.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tyme.feature_dashboard.R
import com.tyme.feature_dashboard.domain.model.TransactionWeek

class ContentAdapter(var items: ArrayList<Int>, var transactionList: List<TransactionWeek>) :
    RecyclerView.Adapter<ContentAdapter.Viewholder>()
{
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var inflator =
            LayoutInflater.from(parent.context).inflate(R.layout.credit_page_item, parent, false)
        when (items[viewType]) {
            // Credit Card page
            0 ->
                inflator = LayoutInflater.from(parent.context).inflate(R.layout.credit_page_item, parent, false)

            // Saving page item
            1 ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.saving_page_item, parent, false)
            // Loadning Page item
            2 ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.loaning_page_item, parent, false)
        }

        context = parent.context
        return Viewholder(inflator)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}