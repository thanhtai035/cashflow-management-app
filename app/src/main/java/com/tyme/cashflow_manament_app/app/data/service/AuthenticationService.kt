package com.tyme.cashflow_manament_app.app.data.service

import com.tyme.cashflow_manament_app.app.data.dto.AuthenticationResponseDto
import com.tyme.cashflow_manament_app.app.domain.model.AuthenticationResponse
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/api/users/login")
    suspend fun login(@Body jsonHolder: JsonHolder): AuthenticationResponseDto
}