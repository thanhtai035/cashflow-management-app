package com.tyme.feature_chatbot.domain


import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.tyme.feature_chatbot.domain.usecase.PostQuestionOpenAIUseCase

internal val domainModule = module {
    singleOf(::PostQuestionOpenAIUseCase)

}
