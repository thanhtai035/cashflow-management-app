package com.tyme.feature_account.domain.model

import com.tyme.feature_account.domain.model.TransactionDetail

data class TransactionDetailPage(
    val transactionDetailList: List<TransactionDetail>,
    val first: Boolean,
    val last: Boolean,
    val size: Int,
) {
    public fun getTransactionList(): List<TransactionDetail> {return transactionDetailList}
}