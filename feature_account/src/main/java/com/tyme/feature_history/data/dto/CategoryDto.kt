package com.tyme.transaction_feature.data.dto

import com.tyme.transaction_feature.domain.model.Category
@kotlinx.serialization.Serializable
data class CategoryDto(
    val entertainment: Int,
    val food: Int,
    val investment: Int,
    val others: Int,
    val total: Int,
    val travel: Int,
    val utilities: Int,
)

fun CategoryDto.toEntity(): Category {
    return Category(
        entertainment = entertainment,
        food = food,
        investment = investment,
        others = others,
        total = total,
        travel = travel,
        utilities = utilities
    )
}