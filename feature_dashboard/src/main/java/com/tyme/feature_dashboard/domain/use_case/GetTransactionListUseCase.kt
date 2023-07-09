package com.tyme.feature_dashboard.domain.use_case

import com.tyme.feature_dashboard.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.tyme.base_feature.common.Result
import com.tyme.feature_dashboard.domain.model.TransactionWeek

class GetTransactionListUseCase (
    private val repository: TransactionRepository
){
    operator fun invoke(userID: String
    ): Flow<Result<List<TransactionWeek>>> = flow {

        try {
            emit(Result.Loading<List<TransactionWeek>>())
            val result = repository.getTransactionMonth(userID)
            emit(Result.Success<List<TransactionWeek>>(result))
        } catch(e: HttpException) {
            emit(Result.Error<List<TransactionWeek>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<List<TransactionWeek>>(e.toString()))
        }
    }
}