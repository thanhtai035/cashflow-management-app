package com.tyme.feature_dashboard.domain.model

@kotlinx.serialization.Serializable
data class TransactionDetail(
    val amount: Double,
    val category: String,
    val placeOfIssue: String,
    val time: Long,
)