package com.tyme.feature_chatbot.domain.usecase

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.tyme.base_feature.common.Result
import com.tyme.feature_chatbot.data.repository.ChatbotRepositoryImpl
import com.tyme.feature_chatbot.domain.repository.ChatbotRepository

class PostQuestionOpenAIUseCase (
    private val repository: ChatbotRepository
){
    operator fun invoke(prompt: String): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading<String>())
            val result = repository.getAnswer(prompt)
            emit(Result.Success(result))
        } catch(e: HttpException) {
            emit(Result.Error<String>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<String>("Couldn't reach server. Check your internet connection."))
        }
    }

}