package com.tyme.feature_transaction.domain.model

import com.tyme.feature_transaction.domain.model.Category

data class TransactionWeek(
    val category: Category,
    val week: Int,
    val month: Int,
    val totalIncome: Double,
    val totalOutcome: Double,
    val year: Int
)