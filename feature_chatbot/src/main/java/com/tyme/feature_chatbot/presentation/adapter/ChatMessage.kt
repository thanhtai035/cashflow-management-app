package com.tyme.feature_chatbot.presentation.adapter

data class ChatMessage(val content: String, val isSentByUser: Boolean, val isWaitingResponse: Boolean = false)
