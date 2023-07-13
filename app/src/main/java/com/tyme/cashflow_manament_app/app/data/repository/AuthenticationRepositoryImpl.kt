package com.tyme.cashflow_manament_app.app.data.repository

import com.tyme.cashflow_manament_app.app.data.dto.toEntity
import com.tyme.cashflow_manament_app.app.data.service.AuthenticationService
import com.tyme.cashflow_manament_app.app.data.service.JsonHolder
import com.tyme.cashflow_manament_app.app.domain.model.AuthenticationResponse
import com.tyme.cashflow_manament_app.app.domain.repository.AuthenticationRepository
import retrofit2.Call

class AuthenticationRepositoryImpl (
    private val authenticationService: AuthenticationService
    ): AuthenticationRepository {
        override suspend fun getAuthenticationResponse(username: String, password: String): AuthenticationResponse {
            return authenticationService.login(JsonHolder(username, password)).toEntity()
        }
}