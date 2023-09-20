package com.tyme.feature_notification.presentation

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.tyme.feature_notification.presentation.viewmodel.*

internal val presentationModule = module {
    viewModelOf(::NotificationViewModel)
}
