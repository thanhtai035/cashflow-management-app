package com.tyme.feature_history.domain


import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.tyme.transaction_feature.domain.use_case.*

internal val domainModule = module {

    singleOf(::GetTransactionListPageUseCase)

    singleOf(::GetTransactionMonthUseCase)
}
