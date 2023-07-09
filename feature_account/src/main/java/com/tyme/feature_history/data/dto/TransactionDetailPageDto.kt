package com.tyme.transaction_feature.data.dto

import com.tyme.transaction_feature.domain.model.TransactionDetail
import com.tyme.transaction_feature.domain.model.TransactionDetailPage
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TransactionDetailPageDto(
    @SerialName("content")
    val transactionDetailList: List<TransactionDetailDto>,
    @SerialName("first")
    val first: Boolean,
    @SerialName("last")
    val last: Boolean,
    @SerialName("size")
    val size: Int,
)

fun TransactionDetailPageDto.toEntity(): TransactionDetailPage {
    return TransactionDetailPage(
        transactionDetailList = this.transactionDetailList.map {it.toEntity()},
        first = this.first,
        last = this.last,
        size = this.size
    )
}
