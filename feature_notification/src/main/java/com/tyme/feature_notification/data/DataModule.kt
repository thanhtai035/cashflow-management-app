package com.tyme.feature_notification.data

import com.tyme.feature_notification.data.repository.NotificationRepositoryImpl
import com.tyme.feature_notification.data.service.NotificationService
import com.tyme.feature_notification.domain.repository.NotificationRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {

    single<NotificationRepository> { NotificationRepositoryImpl(get()) }

    single { get<Retrofit>().create(NotificationService::class.java) }
}
