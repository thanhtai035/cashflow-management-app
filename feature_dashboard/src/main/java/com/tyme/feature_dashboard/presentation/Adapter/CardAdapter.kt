package com.tyme.feature_dashboard.presentation.Adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tyme.feature_dashboard.R
import com.tyme.feature_dashboard.presentation.util.Card
import com.tyme.feature_dashboard.presentation.util.OnItemChangeListener

class CardAdapter(var items: ArrayList<Card>, private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CardAdapter.Viewholder>()
{
    var context: Context? = null
    var itemChangeListener: OnItemChangeListener? = null
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var inflator =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        val item = items[viewType]
        when (item) {
            Card.Credit ->  inflator =
             LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
            Card.Saving ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.card_saving, parent, false)
            Card.Loaning ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.card_loaning, parent, false)
            Card.Debit ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        }
        val params = inflator.layoutParams as ViewGroup.MarginLayoutParams // Cast to MarginLayoutParams
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        params.height = (screenHeight * 0.28).toInt()
        params.width = (params.height * 1.5 ).toInt()

        inflator.layoutParams = params
        context = parent.context
        return Viewholder(inflator)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }

        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams // Cast to MarginLayoutParams

        if (position == items.size - 1) {
            params.setMargins(params.leftMargin, 0,  (Resources.getSystem().displayMetrics.widthPixels * 0.4).toInt(), 0)
        }
        holder.itemView.layoutParams = params

    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
        itemChangeListener?.onItemChanged(position)
    }
    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}