package com.tyme.feature_dashboard.domain.model

@kotlinx.serialization.Serializable
data class TransactionWeek(
    val category: Category,
    val week: Int,
    val month: Int,
    val totalIncome: Double,
    val totalOutcome: Double,
    val year: Int
)