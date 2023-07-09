package com.tyme.transaction_feature.data.service

import com.tyme.transaction_feature.data.dto.TransactionDetailPageDto
import com.tyme.transaction_feature.data.dto.TransactionMonthDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TransactionService {
    @GET("/api/transactions/{userID}")
    suspend fun getTransactionMonth(@Path("userID") userID: String,
                                    @Query("month") month: Int, @Query("year") year: Int) : TransactionMonthDto

    @GET("/api/transactions/content/{userID}")
    suspend fun getTransactionDetailPage(@Path("userID") userID: String,
                                         @Query("pageNum") pageNum: Int, @Query("month") month: Int, @Query("year") year: Int,
                                         @Query("sortType") sortType: String?, @Query("sortDir") sortDir : String?,
                                         @Query("category") category: String?, @Query("keyword") keyword: String?) : TransactionDetailPageDto


}

