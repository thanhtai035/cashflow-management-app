package com.tyme.feature_dashboard.data.repository

import android.util.Log
import com.tyme.feature_dashboard.data.dto.toEntity
import com.tyme.feature_dashboard.data.service.TransactionService
import com.tyme.feature_dashboard.domain.model.TransactionWeek
import com.tyme.feature_dashboard.domain.repository.TransactionRepository

class TransactionMonthRepositoryImpl (
    private val apiService: TransactionService
) : TransactionRepository {
    override suspend fun getTransactionMonth(
        userID: String,
    ): List<TransactionWeek> {
        return apiService.getTransaction(userID).map{ it.toEntity()}
    }
}