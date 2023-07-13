package com.tyme.cashflow_manament_app.app.data.dto

import com.tyme.cashflow_manament_app.app.domain.model.AuthenticationResponse
import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class AuthenticationResponseDto(
    @SerialName("userID")
    val userID: String,
    @SerialName("success")
    val success: Boolean

)

public fun AuthenticationResponseDto.toEntity(): AuthenticationResponse {
    return AuthenticationResponse(
        userID = userID,
        success = success
    )
}