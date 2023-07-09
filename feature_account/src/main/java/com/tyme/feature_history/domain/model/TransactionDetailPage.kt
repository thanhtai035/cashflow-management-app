package com.tyme.transaction_feature.domain.model

data class TransactionDetailPage(
    val transactionDetailList: List<TransactionDetail>,
    val first: Boolean,
    val last: Boolean,
    val size: Int,
)