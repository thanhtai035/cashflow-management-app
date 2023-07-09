package com.tyme.transaction_feature.domain.model

data class Category(
    val entertainment: Int,
    val food: Int,
    val investment: Int,
    val others: Int,
    val total: Int,
    val travel: Int,
    val utilities: Int
)