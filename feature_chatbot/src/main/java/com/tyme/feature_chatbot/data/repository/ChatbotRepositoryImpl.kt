package com.tyme.feature_chatbot.data.repository

import android.util.Log
import com.tyme.feature_chatbot.data.service.ChatbotRequestHolder
import com.tyme.feature_chatbot.data.service.ChatbotService
import com.tyme.feature_chatbot.domain.repository.ChatbotRepository


class  ChatbotRepositoryImpl (
    private val apiService: ChatbotService
) : ChatbotRepository {

    override suspend fun getAnswer(prompt: String): String {
        val answer: String = apiService.getQuestion(ChatbotRequestHolder(prompt)).answer
        return answer
    }

}