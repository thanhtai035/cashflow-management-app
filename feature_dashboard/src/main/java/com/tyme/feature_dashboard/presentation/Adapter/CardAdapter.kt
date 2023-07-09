package com.tyme.feature_dashboard.presentation.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tyme.feature_dashboard.R
import com.tyme.feature_dashboard.presentation.util.Card

class CardAdapter(var items: ArrayList<Card>) :
    RecyclerView.Adapter<CardAdapter.Viewholder>()
{
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var inflator =
            LayoutInflater.from(parent.context).inflate(R.layout.creditcard_item, parent, false)
        val item = items[viewType % items.size]

        when (item) {
            Card.Credit ->  inflator =
            LayoutInflater.from(parent.context).inflate(R.layout.creditcard_item, parent, false)
            Card.Saving ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.savingcard_item, parent, false)
            Card.Loaning ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.loaning_card, parent, false)
            Card.Debit ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.creditcard_item, parent, false)
        }
        context = parent.context
        return Viewholder(inflator)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

    }


    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}