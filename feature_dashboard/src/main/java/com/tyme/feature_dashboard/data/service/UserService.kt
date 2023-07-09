package com.tyme.feature_dashboard.data.service

import com.tyme.feature_dashboard.data.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface UserService {

    @GET("/api/users/{userID}")
    suspend fun getUser(@Path("userID") userID: String) : UserDto

}

