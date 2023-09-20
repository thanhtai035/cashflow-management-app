package com.tyme.feature_notification

import com.tyme.feature_notification.data.dataModule
import com.tyme.feature_notification.domain.domainModule
import com.tyme.feature_notification.presentation.presentationModule


val featureNotificationModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
