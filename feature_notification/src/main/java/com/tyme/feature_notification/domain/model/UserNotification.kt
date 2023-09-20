package com.tyme.feature_notification.domain.model


data class UserNotification(
    val category: String,
    val id: String,
    val message: String,
    val seen: Boolean,
    val timestamp: Int,
    val title: String
)