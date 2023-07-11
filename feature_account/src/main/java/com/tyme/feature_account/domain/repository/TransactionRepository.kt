package com.tyme.feature_account.domain.repository

import com.tyme.feature_account.domain.model.TransactionDetailPage
import com.tyme.feature_account.domain.model.TransactionWeek

interface TransactionRepository {
    suspend fun getTransactionMonth(userID: String) : List<TransactionWeek>

    suspend fun getTransactionDetailPage(userID: String, pageNum: Int, month: Int, year: Int,
                                         sortType: String?, sortDir : String?,
                                         category: String?, keyword: String?): TransactionDetailPage
}