package com.tyme.feature_dashboard.data

import com.tyme.feature_dashboard.data.repository.TransactionMonthRepositoryImpl
import com.tyme.feature_dashboard.data.repository.UserRepositoryImpl
import com.tyme.feature_dashboard.data.service.TransactionService
import com.tyme.feature_dashboard.data.service.UserService
import com.tyme.feature_dashboard.domain.repository.TransactionRepository
import com.tyme.feature_dashboard.domain.repository.UserRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {

    single<TransactionRepository> { TransactionMonthRepositoryImpl(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }

    single { get<Retrofit>().create(TransactionService::class.java) }
    single { get<Retrofit>().create(UserService::class.java) }

}
