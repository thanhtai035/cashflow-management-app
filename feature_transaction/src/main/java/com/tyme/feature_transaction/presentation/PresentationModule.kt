package com.tyme.feature_transaction.presentation

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.tyme.feature_transaction.presentation.viewmodel.*

internal val presentationModule = module {
    viewModelOf(::TransactionViewModel)
}
