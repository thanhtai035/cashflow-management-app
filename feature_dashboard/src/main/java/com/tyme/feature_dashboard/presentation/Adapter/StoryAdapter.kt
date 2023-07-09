package com.tyme.feature_dashboard.presentation.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tyme.feature_dashboard.domain.model.Advertisement
import com.tyme.feature_dashboard.R

class StoryAdapter(var items: ArrayList<Advertisement>) :
    RecyclerView.Adapter<StoryAdapter.Viewholder>() {
    var context: Context? = null
    var buttonClickCallback: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflator =
            LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
        context = parent.context
        return Viewholder(inflator)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.title.setText(items[position].title)
        holder.time.setText(items[position].subtitle)

        val drawableResourceId = holder.itemView.resources.getIdentifier(
            items[position].picAddress,
            "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .into(holder.pic)
        holder.backBtn.setOnClickListener {
            buttonClickCallback?.invoke()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun buttonClickCallback(listener: () -> Unit) {
        buttonClickCallback = listener
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView
        var title: TextView
        var time: TextView
        var backBtn : LinearLayout

        init {
            pic = itemView.findViewById(R.id.story_image)
            title = itemView.findViewById(R.id.story_title)
            time = itemView.findViewById(R.id.story_time)
            backBtn = itemView.findViewById(R.id.backBtn)
        }
    }
}

