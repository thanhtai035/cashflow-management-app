package com.tyme.feature_transaction.presentation.viewmodel

import java.time.LocalDateTime

data class TransactionState(
    val pageNum: Int = 1,
    val month: Int = 8,
    val year: Int = LocalDateTime.now().year,
    val sortDir: String? = null,
    val sortType: String? = null,
    var category: String? = null,
    val keyword: String? = null,
    val income: Boolean? = null
) {

}
