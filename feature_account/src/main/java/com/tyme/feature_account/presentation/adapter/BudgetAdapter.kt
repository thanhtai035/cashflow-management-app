package com.tyme.feature_account.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tyme.feature_account.R
import com.tyme.feature_account.presentation.util.Item

class BudgetAdapter(private val context: Context, private val itemList: List<Item>, private val clickListener: OnClickListener) :
    RecyclerView.Adapter<BudgetAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.budget_item, parent, false)
        return MyViewHolder(itemView)
    }
    interface OnClickListener {
        fun onItemClick(item: Item, position: Int)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]
        val imagesrc = when (item.categoryName) {
            "Food" -> R.drawable.lunch_dining
            "Investment" -> R.drawable.ic_investment
            "Entertainment" -> R.drawable.ic_entertainment
            "Others" -> R.drawable.ic_others
            "Travel" -> R.drawable.ic_travel
            "Utilities" -> R.drawable.ic_utilities
            else -> {
                R.drawable.lunch_dining
            }
        }
        val backgroundColor = when (item.categoryName) {
            "Food" ->
                Color.parseColor("#2c5dca") // Custom color for Entry 1

            "Investment" ->
                Color.parseColor("#03A9F4") // Custom color for Entry 2

            "Entertainment" ->
            Color.parseColor("#4CAF50") // Custom color for Entry 3

            "Others" ->
                Color.parseColor("#9C27B0") // Custom color for Entry 4

            "Travel" ->
                Color.parseColor("#FF5722")// Custom color for Entry 5

            "Utilities" ->
                Color.parseColor("#E91E63") // Custom color for Entry 6
            else -> {
                Color.parseColor("#E91E63") // Custom color for Entry 6
            }
        }
        if(item.categoryAmount > item.spendAmount) {
            holder.warningImg.visibility = View.GONE
        }
        holder.layout.setBackgroundColor(backgroundColor)
        holder.cateIcon.setImageResource(imagesrc)
        holder.cateName.text = item.categoryName
        holder.spendAmount.text = "$" + item.spendAmount.toString()
        holder.cateAmount.text = "of $" + item.categoryAmount.toString()
    }

    override fun getItemCount() = itemList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cateIcon: ImageView = itemView.findViewById(R.id.cateIcon)
        val cateName: TextView = itemView.findViewById(R.id.cateName)
        val cateAmount: TextView = itemView.findViewById(R.id.cateAmount)
        val spendAmount: TextView = itemView.findViewById(R.id.spendAmount)
        val warningImg: ImageView = itemView.findViewById(R.id.warningImg)

        val layout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)

        init {
            itemView.setOnClickListener {
                val item = itemList[adapterPosition]
                if (item != null) {
                    // Notify the clickListener with the entire item object
                    clickListener.onItemClick(item, adapterPosition)
                }
            }
        }
    }
}
