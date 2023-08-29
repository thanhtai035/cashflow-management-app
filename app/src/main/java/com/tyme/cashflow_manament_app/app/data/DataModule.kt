package com.tyme.cashflow_manament_app.app.data

import com.tyme.cashflow_manament_app.app.data.repository.AuthenticationRepositoryImpl
import com.tyme.cashflow_manament_app.app.data.service.AuthenticationService
import com.tyme.cashflow_manament_app.app.domain.repository.AuthenticationRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {

    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get()) }

    single { get<Retrofit>().create(AuthenticationService::class.java) }
}
