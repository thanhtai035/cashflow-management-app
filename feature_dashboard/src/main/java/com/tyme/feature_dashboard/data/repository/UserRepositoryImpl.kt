package com.tyme.feature_dashboard.data.repository

import android.util.Log
import com.tyme.feature_dashboard.data.dto.toEntity
import com.tyme.feature_dashboard.data.service.TransactionService
import com.tyme.feature_dashboard.data.service.UserService
import com.tyme.feature_dashboard.domain.model.AnalysisResponse
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
        val user = apiService.getUser(userID).toEntity()
        return user
    }

    override suspend fun getAnalysis(userID: String): AnalysisResponse {
        return apiService.getAnalysis(userID).toEntity()
    }
}