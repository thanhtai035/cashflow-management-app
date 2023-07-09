package com.tyme.feature_dashboard.domain.repository

import com.tyme.feature_dashboard.domain.model.TransactionWeek
import com.tyme.feature_dashboard.domain.model.User


interface UserRepository {
    suspend fun getUser(userID: String) : User
}