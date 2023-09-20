package com.tyme.feature_dashboard.data.service

import com.tyme.feature_dashboard.data.dto.TransactionDetailDto
import com.tyme.feature_dashboard.data.dto.TransactionDetailPageDto
import com.tyme.feature_dashboard.data.dto.TransactionWeekDto
import com.tyme.feature_dashboard.domain.model.TransactionWeek
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TransactionService {

    @GET("/api/transactions/{userID}")
    suspend fun getTransaction(@Path("userID") userID: String) : List<TransactionWeekDto>

    @GET("/api/transactions/current/{userID}")
    suspend fun getTransactionDetailPage(@Path("userID") userID: String
                                         ) : List<TransactionDetailDto>

}

