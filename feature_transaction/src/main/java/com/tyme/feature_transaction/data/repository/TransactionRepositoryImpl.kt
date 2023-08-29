package com.tyme.feature_transaction.data.repository

import com.tyme.feature_transaction.data.dto.toEntity
import com.tyme.feature_transaction.data.service.TransactionService
import com.tyme.feature_transaction.domain.model.TransactionDetailPage
import com.tyme.feature_transaction.domain.model.TransactionWeek
import com.tyme.feature_transaction.domain.repository.TransactionRepository

class   TransactionRepositoryImpl (
    private val apiService: TransactionService
) : TransactionRepository {
    override suspend fun getTransactionMonth(
        userID: String,
    ): List<TransactionWeek> {
        return apiService.getTransaction(userID).map{ it.toEntity()}
    }

    override suspend fun getTransactionDetailPage(userID: String, pageNum: Int, month: Int, year: Int,
                                                  sortType: String?, sortDir : String?,
                                                  category: String?, keyword: String?): TransactionDetailPage {
            return  apiService.getTransactionDetailPage(userID, pageNum, month, year, sortType, sortDir, category, keyword).toEntity()
    }
}