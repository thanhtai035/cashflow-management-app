package com.tyme.feature_dashboard.data.repository

import android.util.Log
import com.tyme.feature_dashboard.data.dto.toEntity
import com.tyme.feature_dashboard.data.service.TransactionService
import com.tyme.feature_dashboard.data.service.UserService
import com.tyme.feature_dashboard.domain.model.TransactionWeek
import com.tyme.feature_dashboard.domain.model.User
import com.tyme.feature_dashboard.domain.repository.TransactionRepository
import com.tyme.feature_dashboard.domain.repository.UserRepository

class UserRepositoryImpl (
    private val apiService: UserService
) : UserRepository {
    override suspend fun getUser(
        userID: String,
    ): User {
        return apiService.getUser(userID).toEntity()
    }
}