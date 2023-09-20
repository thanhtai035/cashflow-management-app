package com.tyme.feature_notification.presentation.adapter

import android.app.Notification
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tyme.feature_notification.R
import com.tyme.feature_notification.domain.model.UserNotification
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class NotificationAdapter(
    private val context: Context,
    private val notifications: List<UserNotification>
) : BaseAdapter() {

    override fun getCount(): Int = notifications.size

    override fun getItem(position: Int): Any = notifications[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false)

        val notification = getItem(position) as UserNotification

        val titleTextView: TextView = view.findViewById(R.id.notiTitle)
        val contentTextView: TextView = view.findViewById(R.id.content)
        val timeTextView: TextView = view.findViewById(R.id.notiTime)

        titleTextView.text = notification.title
        contentTextView.text = notification.message

        val prettyTime = PrettyTime(Locale.ENGLISH)
        val timestampInMillis = notification.timestamp * 1000L // Convert seconds to milliseconds
        timeTextView.text = prettyTime.format(Calendar.getInstance().apply {
            timeInMillis = timestampInMillis
        }.time)

        return view
    }
}
