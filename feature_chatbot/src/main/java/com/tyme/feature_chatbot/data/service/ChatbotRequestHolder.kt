package com.tyme.feature_chatbot.data.service

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class ChatbotRequestHolder (
    @SerialName("prompt")
    val prompt: String
)
