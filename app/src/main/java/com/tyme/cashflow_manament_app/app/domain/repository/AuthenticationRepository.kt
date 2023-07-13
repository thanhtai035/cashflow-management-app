package com.tyme.cashflow_manament_app.app.domain.repository

import com.tyme.cashflow_manament_app.app.domain.model.AuthenticationResponse
import retrofit2.Call

interface AuthenticationRepository {
    suspend fun getAuthenticationResponse(username: String, password: String) : AuthenticationResponse
}