package com.tyme.cashflow_manament_app.app.data

import com.tyme.cashflow_manament_app.app.data.repository.AuthenticationRepositoryImpl
import com.tyme.cashflow_manament_app.app.data.service.AuthenticationService
import com.tyme.cashflow_manament_app.app.domain.repository.AuthenticationRepository
import com.tyme.feature_account.data.repository.TransactionRepositoryImpl
import com.tyme.feature_account.data.service.TransactionService
import com.tyme.feature_account.domain.repository.TransactionRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {

    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get()) }

    single { get<Retrofit>().create(AuthenticationService::class.java) }
}
