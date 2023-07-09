package com.tyme.feature_dashboard.domain


import com.tyme.feature_dashboard.domain.use_case.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {

    singleOf(::GetTransactionListUseCase)

    singleOf(::GetUserUseCase)

}
