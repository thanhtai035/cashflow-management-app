package com.tyme.feature_notification.data.service


import com.tyme.feature_notification.data.dto.UserNotificationDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NotificationService {


    @GET("/api/notification/{userID}")
    suspend fun getNotificationPage(@Path("userID") userID: String,
                                         @Query("pageNum") pageNum: Int) : List<UserNotificationDto>


}

