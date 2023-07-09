package com.tyme.transaction_feature.domain.model

data class TransactionDetail(
    val amount: Double,
    val category: String,
    val placeOfIssue: String,
    val time: String,
)