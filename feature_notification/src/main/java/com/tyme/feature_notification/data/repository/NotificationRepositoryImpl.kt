package com.tyme.feature_notification.data.repository

import com.tyme.feature_notification.data.dto.toEntity
import com.tyme.feature_notification.data.service.NotificationService
import com.tyme.feature_notification.domain.model.UserNotification
import com.tyme.feature_notification.domain.repository.NotificationRepository


class   NotificationRepositoryImpl (
    private val apiService: NotificationService
) : NotificationRepository {
    override suspend fun getNotifications(userID: String, pageNum: Int): List<UserNotification> {
        return apiService.getNotificationPage(userID, pageNum).map{ it.toEntity()}
    }
}