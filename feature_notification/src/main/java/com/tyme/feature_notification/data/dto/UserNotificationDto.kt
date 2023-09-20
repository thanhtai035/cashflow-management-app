package com.tyme.feature_notification.data.dto

import com.tyme.feature_notification.domain.model.UserNotification

@kotlinx.serialization.Serializable
data class UserNotificationDto(
    val category: String,
    val id: String,
    val message: String,
    val seen: Boolean,
    val timestamp: Int,
    val title: String
)

fun UserNotificationDto.toEntity(): UserNotification {
    return UserNotification(
        category = category, id = id, message = message, seen = seen, timestamp = timestamp, title = title
    )
}