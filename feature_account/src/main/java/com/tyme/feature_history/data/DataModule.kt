package com.tyme.feature_history.data

import com.tyme.transaction_feature.data.repository.TransactionRepositoryImpl
import com.tyme.transaction_feature.data.service.TransactionService
import com.tyme.transaction_feature.domain.repository.TransactionRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {

    single<TransactionRepository> { TransactionRepositoryImpl(get()) }

    single { get<Retrofit>().create(TransactionService::class.java) }
}
