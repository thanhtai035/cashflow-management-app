package com.tyme.feature_account.data.dto

import com.tyme.feature_account.domain.model.TransactionDetail
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TransactionDetailDto(
    @SerialName("amount")
    val amount: Double,
    @SerialName("category")
    val category: String,
    @SerialName("placeOfIssue")
    val placeOfIssue: String,
    @SerialName("timestamp")
    val time: Long,
)

fun TransactionDetailDto.toEntity(): TransactionDetail {
    return TransactionDetail(
        amount = this.amount,
        category = this.category,
        placeOfIssue = this.placeOfIssue,
        time = this.time,
    )
}
