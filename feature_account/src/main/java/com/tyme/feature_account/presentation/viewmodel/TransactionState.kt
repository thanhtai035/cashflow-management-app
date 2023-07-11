package com.tyme.feature_account.presentation.viewmodel

import java.time.LocalDateTime

data class TransactionState(
    val pageNum: Int = 1,
    val month: Int = LocalDateTime.now().monthValue,
    val year: Int = LocalDateTime.now().year,
    val sortDir: String? = null,
    val sortType: String? = null,
    var category: String? = null,
    val keyword: String? = null
) {

}
