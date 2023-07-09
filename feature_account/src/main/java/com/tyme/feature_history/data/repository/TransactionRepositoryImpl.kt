package com.tyme.transaction_feature.data.repository

import com.tyme.transaction_feature.data.dto.toEntity
import com.tyme.transaction_feature.data.service.TransactionService
import com.tyme.transaction_feature.domain.model.TransactionDetailPage
import com.tyme.transaction_feature.domain.model.TransactionMonth
import com.tyme.transaction_feature.domain.repository.TransactionRepository

import retrofit2.HttpException
import java.io.IOException

class   TransactionRepositoryImpl (
    private val apiService: TransactionService
) : TransactionRepository {
    override suspend fun getTransactionMonth(userID: String, month: Int, year: Int) : TransactionMonth {
            val transactionMonth = apiService.getTransactionMonth(userID, month, year).toEntity()
            return transactionMonth
    }

    override suspend fun getTransactionDetailPage(userID: String, pageNum: Int, month: Int, year: Int,
                                                  sortType: String?, sortDir : String?,
                                                  category: String?, keyword: String?): TransactionDetailPage {
            val transactiondetailPage = apiService.getTransactionDetailPage(userID, pageNum, month, year, sortType, sortDir, category, keyword).toEntity()
            return transactiondetailPage
    }
}