package com.tyme.feature_notification.domain

import com.tyme.feature_notification.domain.use_case.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
internal val domainModule = module {
    singleOf(::GetNotificationUseCase)

}
