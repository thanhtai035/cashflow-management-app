package com.tyme.feature_chatbot


import com.tyme.feature_chatbot.data.dataModule
import com.tyme.feature_chatbot.domain.domainModule
import com.tyme.feature_chatbot.presentation.presentationModule

val featureChatBotModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
