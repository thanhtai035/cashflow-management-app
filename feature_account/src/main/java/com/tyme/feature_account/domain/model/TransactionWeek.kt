package com.tyme.feature_account.domain.model

data class TransactionWeek(
    val category: Category,
    val week: Int,
    val month: Int,
    val totalIncome: Double,
    val totalOutcome: Double,
    val year: Int
)