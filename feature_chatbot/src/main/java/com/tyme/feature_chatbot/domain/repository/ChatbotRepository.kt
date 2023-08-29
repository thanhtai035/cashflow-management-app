package com.tyme.feature_chatbot.domain.repository


interface ChatbotRepository {
    suspend fun getAnswer(prompt: String) : String

}