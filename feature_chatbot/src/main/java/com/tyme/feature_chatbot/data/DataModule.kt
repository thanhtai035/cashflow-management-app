package com.tyme.feature_chatbot.data

import com.tyme.feature_chatbot.data.repository.ChatbotRepositoryImpl
import com.tyme.feature_chatbot.data.service.ChatbotService
import com.tyme.feature_chatbot.domain.repository.ChatbotRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {

    single<ChatbotRepository> { ChatbotRepositoryImpl(get()) }

    single { get<Retrofit>().create(ChatbotService::class.java) }
}
