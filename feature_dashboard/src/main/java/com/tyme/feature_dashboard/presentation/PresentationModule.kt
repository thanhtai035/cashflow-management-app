package com.tyme.feature_dashboard.presentation

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.tyme.feature_dashboard.presentation.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

internal val presentationModule = module {
    viewModelOf(::DashboardViewModel)
}
