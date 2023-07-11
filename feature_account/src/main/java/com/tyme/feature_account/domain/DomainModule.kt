package com.tyme.feature_account.domain


import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.tyme.feature_account.domain.use_case.*
internal val domainModule = module {

    singleOf(::GetTransactionListUseCase)

    singleOf(::GetTransactionDetailPageUseCase)

}
