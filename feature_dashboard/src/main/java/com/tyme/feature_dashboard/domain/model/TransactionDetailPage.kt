package com.tyme.feature_dashboard.domain.model

data class TransactionDetailPage(
    val transactionDetailList: List<TransactionDetail>,
    val first: Boolean,
    val last: Boolean,
    val size: Int,
    val totalPage: Int
) {
    public fun getTransactionList(): List<TransactionDetail> {return transactionDetailList}
}