package com.tyme.feature_account.data.dto

import com.tyme.feature_account.domain.model.Category

@kotlinx.serialization.Serializable
data class CategoryDto(
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

fun CategoryDto.toEntity(): Category {
    return Category(
        entertainment = entertainment,
        entertainmentAmount = entertainmentAmount,
        food = food,
        foodAmount = foodAmount,
        investment = investment,
        investmentAmount = investmentAmount,
        others = others,
        othersAmount = othersAmount,
        total = total,
        travel = travel,
        travelAmount = travelAmount,
        utilities = utilities,
        utilitiesAmount = utilitiesAmount
    )
}