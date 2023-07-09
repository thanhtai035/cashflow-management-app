package com.tyme.transaction_feature.data.dto

import com.tyme.transaction_feature.domain.model.TransactionMonth
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TransactionMonthDto(
    @SerialName("category")
    val category: CategoryDto,
    @SerialName("month")
    val month: Int,
    @SerialName("totalIncome")
    val totalIncome: Double,
    @SerialName("totalOutcome")
    val totalOutcome: Double,
    @SerialName("year")
    val year: Int
)

fun TransactionMonthDto.toEntity(): TransactionMonth {
    return TransactionMonth(
        category = this.category.toEntity(),
        month = this.month,
        totalIncome = this.totalIncome,
        totalOutcome = this.totalOutcome,
        year = this.year,
    )
}