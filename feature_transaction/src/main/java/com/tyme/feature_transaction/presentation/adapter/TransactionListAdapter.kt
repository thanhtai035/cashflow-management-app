package com.tyme.feature_transaction.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tyme.feature_transaction.R
import com.tyme.feature_transaction.domain.model.TransactionDetail
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class TransactionListAdapter(var items: List<TransactionDetail>) :
    RecyclerView.Adapter<TransactionListAdapter.Viewholder>()
{
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var inflator =
            LayoutInflater.from(parent.context).inflate(R.layout.listview_item, parent, false)
        return Viewholder(inflator)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val transactionDetail = items[position]

        holder.issuePlace.text = transactionDetail.placeOfIssue

        if (position == 0)
            holder.divider.visibility = View.GONE
        val str = transactionDetail.amount.toString() + " USD"
        holder.amount.text = str
        if(transactionDetail.amount < 0) {
            holder.amount.setTextColor(Color.RED)
        }

        val timeConvert = LocalDateTime.ofInstant(Instant.ofEpochSecond(transactionDetail.time), ZoneOffset.UTC )
        val formatter = DateTimeFormatter.ofPattern("hh:mm a - MMM dd, yyyy")

        holder.time.text = timeConvert.format(formatter).toString()
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val logo: ImageView = itemView.findViewById(R.id.logo)
        val issuePlace: TextView = itemView.findViewById(R.id.issuePlace)
        val divider: View = itemView.findViewById(R.id.divider)
        val time: TextView = itemView.findViewById(R.id.time)
        val amount: TextView = itemView.findViewById(R.id.amount)
    }
}