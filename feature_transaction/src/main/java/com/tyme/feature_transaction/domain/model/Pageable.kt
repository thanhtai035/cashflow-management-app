package com.tyme.feature_transaction.domain.model

data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean
)