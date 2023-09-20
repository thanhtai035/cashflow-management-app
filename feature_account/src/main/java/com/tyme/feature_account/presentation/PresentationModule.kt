package com.tyme.feature_account.presentation

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.tyme.feature_account.presentation.viewmodel.BudgetViewModel

internal val presentationModule = module {
    viewModelOf(::BudgetViewModel)
}
