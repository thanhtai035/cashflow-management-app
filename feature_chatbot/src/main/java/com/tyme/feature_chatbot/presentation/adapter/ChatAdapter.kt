package com.tyme.feature_chatbot.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.github.ybq.android.spinkit.SpinKitView
import com.tyme.feature_chatbot.R

// List adapter for messages

class ChatAdapter(private val mContext: Context, private val dataSource: List<ChatMessage>)
    : ArrayAdapter<ChatMessage>(mContext, 0, dataSource) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val message = getItem(position)

        val layout = if (message?.isSentByUser != false) R.layout.received_message else R.layout.send_message

        val view = LayoutInflater.from(mContext).inflate(layout, parent, false)
        val textView: TextView = view.findViewById(R.id.messageTextView)
        if (message != null) {
            textView.text = message.content
        }

        if (message != null && layout == R.layout.send_message) {
            val loader: SpinKitView = view.findViewById(R.id.chatbot_loading)

            if (message.isWaitingResponse) {
                loader.visibility = View.VISIBLE
                textView.visibility = View.GONE
            } else {
                loader.visibility = View.GONE
                textView.visibility = View.VISIBLE

            }
        }

        return view
    }
}
