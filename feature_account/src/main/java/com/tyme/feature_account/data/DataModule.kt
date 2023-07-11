package com.tyme.feature_account.data

import com.tyme.feature_account.data.repository.TransactionRepositoryImpl
import com.tyme.feature_account.data.service.TransactionService
import com.tyme.feature_account.domain.repository.TransactionRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {

    single<TransactionRepository> { TransactionRepositoryImpl(get()) }

    single { get<Retrofit>().create(TransactionService::class.java) }
}
