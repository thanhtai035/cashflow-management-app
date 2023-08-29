package com.tyme.feature_chatbot.data.service

import retrofit2.http.*

interface ChatbotService {
    @POST("/api/chatbot")
    suspend fun getQuestion(@Body prompt: ChatbotRequestHolder): ChatbotResponseHolder
}

