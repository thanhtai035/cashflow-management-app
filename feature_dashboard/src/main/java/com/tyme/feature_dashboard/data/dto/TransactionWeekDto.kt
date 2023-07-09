package com.tyme.feature_dashboard.data.dto

import com.tyme.feature_dashboard.domain.model.TransactionWeek
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TransactionWeekDto(
    @SerialName("category")
    val category: CategoryDto,
    @SerialName("week")
    val week: Int,
    @SerialName("month")
    val month: Int,
    @SerialName("totalIncome")
    val totalIncome: Double,
    @SerialName("totalOutcome")
    val totalOutcome: Double,
    @SerialName("year")
    val year: Int
)

fun TransactionWeekDto.toEntity(): TransactionWeek {
    return TransactionWeek(
        category = this.category.toEntity(),
        week = this.week,
        month = this.month,
        totalIncome = this.totalIncome,
        totalOutcome = this.totalOutcome,
        year = this.year,
    )
}