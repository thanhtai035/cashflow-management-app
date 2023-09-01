package com.tyme.feature_dashboard.presentation.Adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.tyme.feature_dashboard.R


class MyMarkerView(context: Context?, layoutResource: Int) :
    MarkerView(context, layoutResource) {
    private val tvContent: TextView

    init {
        // this markerview only displays a textview
        tvContent = findViewById<View>(R.id.tvContent) as TextView
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        // here you can change whatever you want to show in following line as x/y or both
        tvContent.text = e.y.toString() // set the entry-value as the display text
    }
}