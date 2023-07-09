package com.tyme.feature_dashboard.presentation.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.tyme.feature_dashboard.domain.model.Advertisement
import com.tyme.feature_dashboard.R

class AdsAdapter(var items: ArrayList<Advertisement>) :
    RecyclerView.Adapter<AdsAdapter.Viewholder>() {
    var context: Context? = null
    private var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflator =
            LayoutInflater.from(parent.context).inflate(R.layout.advertisement_item, parent, false)
        context = parent.context
        return Viewholder(inflator)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val drawableResourceId = holder.itemView.resources.getIdentifier(
            items[position].picAddress,
            "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .transform(GranularRoundedCorners(30f, 30f, 0f, 0f))
            .into(holder.pic)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView

        init {
            pic = itemView.findViewById(R.id.pic)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(position)
                }
            }
        }
    }
}