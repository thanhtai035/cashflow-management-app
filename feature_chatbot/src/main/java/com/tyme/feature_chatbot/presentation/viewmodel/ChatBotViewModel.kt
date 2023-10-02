package com.tyme.feature_chatbot.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyme.feature_chatbot.domain.usecase.PostQuestionOpenAIUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.tyme.base_feature.common.Result

class ChatBotViewModel(
    private val postQuestionOpenAIUseCase: PostQuestionOpenAIUseCase
) : ViewModel() {

    val receivedMessages: MutableLiveData<Result<String>> = MutableLiveData()

    public fun getMessage(prompt: String){
        postQuestionOpenAIUseCase(prompt).onEach {
            result ->
            receivedMessages.postValue(result)
        }.launchIn(viewModelScope)
    }

}
