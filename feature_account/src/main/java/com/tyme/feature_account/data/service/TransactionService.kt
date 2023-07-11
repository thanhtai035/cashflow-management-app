package com.tyme.feature_account.data.service

import com.tyme.feature_account.data.dto.TransactionDetailPageDto
import com.tyme.feature_account.data.dto.TransactionWeekDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TransactionService {
    @GET("/api/transactions/{userID}")
    suspend fun getTransaction(@Path("userID") userID: String) : List<TransactionWeekDto>

    @GET("/api/transactions/content/{userID}")
    suspend fun getTransactionDetailPage(@Path("userID") userID: String,
                                         @Query("pageNum") pageNum: Int, @Query("month") month: Int, @Query("year") year: Int,
                                         @Query("sortType") sortType: String?, @Query("sortDir") sortDir : String?,
                                         @Query("category") category: String?, @Query("keyword") keyword: String?) : TransactionDetailPageDto


}

