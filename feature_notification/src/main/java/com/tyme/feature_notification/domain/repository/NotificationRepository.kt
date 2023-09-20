package com.tyme.feature_notification.domain.repository


import com.tyme.feature_notification.domain.model.UserNotification

interface NotificationRepository {
    suspend fun getNotifications(userID: String, pageNum: Int) : List<UserNotification>

}