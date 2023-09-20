package com.tyme.feature_transaction.data.dto

import com.tyme.feature_transaction.domain.model.TransactionDetailPage
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
    @SerialName("totalPages")
    val totalPage: Int

)

fun TransactionDetailPageDto.toEntity(): TransactionDetailPage {
    return TransactionDetailPage(
        transactionDetailList = this.transactionDetailList.map {it.toEntity()},
        first = this.first,
        last = this.last,
        size = this.size,
        totalPage = this.totalPage
    )
}
