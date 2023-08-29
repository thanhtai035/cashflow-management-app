package com.tyme.feature_transaction.domain


import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.tyme.feature_transaction.domain.use_case.GetTransactionDetailPageUseCase
import com.tyme.feature_transaction.domain.use_case.GetTransactionListUseCase

internal val domainModule = module {

    singleOf(::GetTransactionListUseCase)

    singleOf(::GetTransactionDetailPageUseCase)

}
