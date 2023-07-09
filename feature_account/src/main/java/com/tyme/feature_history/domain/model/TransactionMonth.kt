package com.tyme.transaction_feature.domain.model

data class TransactionMonth(
    val category: Category,
    val month: Int,
    val totalIncome: Double,
    val totalOutcome: Double,
    val year: Int
)