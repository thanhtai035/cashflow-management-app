package com.tyme.feature_account.domain.model

data class Category(
    val entertainment: Int,
    val entertainmentAmount: Float,
    val food: Int,
    val foodAmount: Float,
    val investment: Int,
    val investmentAmount: Float,
    val others: Int,
    val othersAmount: Float,
    val travel: Int,
    val travelAmount: Float,
    val utilities: Int,
    val utilitiesAmount: Float,
    val total: Int,
    )