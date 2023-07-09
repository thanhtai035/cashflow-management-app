package com.tyme.feature_dashboard.data.dto

import com.tyme.feature_dashboard.domain.model.TransactionWeek
import com.tyme.feature_dashboard.domain.model.User

@kotlinx.serialization.Serializable
data class UserDto(
    val balance: Double,
    val debitCard: String?,
    val firstName: String,
    val lastName: String,
    val loaningCard: String?,
    val savingCard: String?,
    val username: String
)

fun UserDto.toEntity(): User {
    return User(
        balance = this.balance,
        debitCard = this.debitCard,
        firstName = this.firstName,
        lastName = this.lastName,
        loaningCard = this.loaningCard,
        savingCard = this.savingCard,
        username = this.username
    )
}