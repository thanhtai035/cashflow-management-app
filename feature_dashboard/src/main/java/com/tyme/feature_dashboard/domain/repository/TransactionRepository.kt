package com.tyme.feature_dashboard.domain.repository

import com.tyme.feature_dashboard.domain.model.TransactionWeek


interface TransactionRepository {
    suspend fun getTransactionMonth(userID: String) : List<TransactionWeek>
}