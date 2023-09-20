//package com.tyme.feature_dashboard.presentation.Adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.view.marginRight
//import androidx.recyclerview.widget.RecyclerView
//import com.tyme.feature_dashboard.R
//import com.tyme.feature_dashboard.presentation.util.OnItemChangeListener
//
//class NavigateAdapter(private val items: IntArray,
//                      private val itemClickListener: OnItemClickListener) :
//    RecyclerView.Adapter<NavigateAdapter.ViewHolder>() {
//
//    interface OnItemClickListener {
//        fun onItemClick(position: Int)
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val context = parent.context
//        val inflater = LayoutInflater.from(context)
//
//        // Inflate the layout based on the viewType
//        val layoutId = when (viewType) {
//            1 -> R.layout.transaction_item
//            2 -> R.layout.budget_item
//            else -> R.layout.transaction_item
//        }
//
//        val view = inflater.inflate(layoutId, parent, false)
//
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.itemView.setOnClickListener {
//            itemClickListener.onItemClick(position)
//        }
//        // Bind data to the views here
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return items[position]
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }
//}
