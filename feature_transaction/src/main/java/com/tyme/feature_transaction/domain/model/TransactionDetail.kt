package com.tyme.feature_transaction.domain.model

data class TransactionDetail(
    val amount: Double,
    val category: String,
    val placeOfIssue: String,
    val time: Long,
)