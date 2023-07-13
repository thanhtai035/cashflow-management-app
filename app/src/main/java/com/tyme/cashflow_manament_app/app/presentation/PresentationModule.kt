package com.tyme.cashflow_manament_app.app.presentation

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.tyme.cashflow_manament_app.app.presentation.authentication.*
import org.koin.androidx.viewmodel.dsl.viewModel

internal val presentationModule = module {
    viewModelOf(::AuthenticationViewModel)
}
