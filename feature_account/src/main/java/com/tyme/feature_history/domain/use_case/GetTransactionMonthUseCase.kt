package com.tyme.transaction_feature.domain.use_case

import com.tyme.transaction_feature.domain.model.TransactionMonth
import com.tyme.transaction_feature.domain.repository.TransactionRepository

import com.tyme.transaction_feature.domain.model.TransactionDetailPage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.tyme.base_feature.common.Result

class GetTransactionMonthUseCase (
    private val repository: TransactionRepository
){
    operator fun invoke(userID: String, pageNum: Int, month: Int, year: Int,
                        sortType: String, sortDir : String,
                        category: String, keyword: String): Flow<Result<TransactionMonth>> = flow {
        try {
            emit(Result.Loading<TransactionMonth>())
            val result = repository.getTransactionMonth(userID, pageNum, month)
            emit(Result.Success<TransactionMonth>(result))
        } catch(e: HttpException) {
            emit(Result.Error<TransactionMonth>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<TransactionMonth>("Couldn't reach server. Check your internet connection."))
        }
    }
}