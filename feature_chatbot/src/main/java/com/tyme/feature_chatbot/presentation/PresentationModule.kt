package com.tyme.feature_chatbot.presentation

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.tyme.feature_chatbot.presentation.viewmodel.ChatBotViewModel

internal val presentationModule = module {
    viewModelOf(::ChatBotViewModel)
}
