package com.tyme.feature_dashboard.domain.use_case


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.tyme.base_feature.common.Result
import com.tyme.feature_dashboard.domain.model.TransactionDetail
import com.tyme.feature_dashboard.domain.model.TransactionDetailPage
import com.tyme.feature_dashboard.domain.repository.TransactionRepository

class GetTransactionDetailPageUseCase (
    private val repository: TransactionRepository
){
    operator fun invoke(userID: String
                                ): Flow<Result<List<TransactionDetail>>> = flow {
        try {
            emit(Result.Loading<List<TransactionDetail>>())
            val result = repository.getTransactionDetailPage(userID)
            emit(Result.Success<List<TransactionDetail>>(result))
        } catch(e: HttpException) {
            emit(Result.Error<List<TransactionDetail>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<List<TransactionDetail>>("Couldn't reach server. Check your internet connection."))
        }
    }

}