package com.tyme.transaction_feature.domain.repository

import com.tyme.transaction_feature.domain.model.TransactionDetailPage
import com.tyme.transaction_feature.domain.model.TransactionMonth

interface TransactionRepository {
    suspend fun getTransactionMonth(userID: String, month: Int, year: Int) : TransactionMonth

    suspend fun getTransactionDetailPage(userID: String, pageNum: Int, month: Int, year: Int,
                                         sortType: String?, sortDir : String?,
                                         category: String?, keyword: String?): TransactionDetailPage
}