package com.tyme.transaction_feature.data.dto

import com.tyme.transaction_feature.domain.model.TransactionDetail
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TransactionDetailDto(
    @SerialName("amount")
    val amount: Double,
    @SerialName("category")
    val category: String,
    @SerialName("placeOfIssue")
    val placeOfIssue: String,
    @SerialName("time")
    val time: String,
)

fun TransactionDetailDto.toEntity(): TransactionDetail {
    return TransactionDetail(
        amount = this.amount,
        category = this.category,
        placeOfIssue = this.placeOfIssue,
        time = this.time,
    )
}
