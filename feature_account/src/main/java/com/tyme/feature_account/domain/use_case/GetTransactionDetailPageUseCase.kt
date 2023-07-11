package com.tyme.feature_account.domain.use_case

import com.tyme.feature_account.domain.model.TransactionDetailPage
import com.tyme.feature_account.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.tyme.base_feature.common.Result

class GetTransactionDetailPageUseCase (
    private val repository: TransactionRepository
){
    operator fun invoke(userID: String, pageNum: Int, month: Int, year: Int,
                                sortType: String?, sortDir : String?,
                                category: String?, keyword: String?): Flow<Result<TransactionDetailPage>> = flow {
        try {
            emit(Result.Loading<TransactionDetailPage>())
            val result = repository.getTransactionDetailPage(userID, pageNum, month, year, sortType, sortDir, category, keyword)
            emit(Result.Success<TransactionDetailPage>(result))
        } catch(e: HttpException) {
            emit(Result.Error<TransactionDetailPage>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<TransactionDetailPage>("Couldn't reach server. Check your internet connection."))
        }
    }

}