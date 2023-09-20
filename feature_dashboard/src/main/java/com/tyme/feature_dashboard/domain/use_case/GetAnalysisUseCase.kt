package com.tyme.feature_dashboard.domain.use_case

import android.util.Log
import com.tyme.feature_dashboard.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.tyme.base_feature.common.Result
import com.tyme.feature_dashboard.domain.model.TransactionWeek
import com.tyme.feature_dashboard.domain.model.AnalysisResponse
import com.tyme.feature_dashboard.domain.repository.UserRepository

class GetAnalysisUseCase (
    private val repository: UserRepository
){
    operator fun invoke(userID: String
    ): Flow<Result<AnalysisResponse>> = flow {

        try {
            emit(Result.Loading<AnalysisResponse>())
            val result = repository.getAnalysis(userID)
            emit(Result.Success<AnalysisResponse>(result))
        } catch(e: HttpException) {
            Log.d("tai", e.message())
            emit(Result.Error<AnalysisResponse>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            Log.d("tai", e.toString())
            emit(Result.Error<AnalysisResponse>(e.toString()))
        }
    }
}