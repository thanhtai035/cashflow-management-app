package com.tyme.feature_history.presentation

import coil.ImageLoader
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.tyme.feature_history.presentation.TransactionHistory.TransactionDetailViewModel

internal val presentationModule = module {

    viewModelOf(::TransactionDetailViewModel)

    single { ImageLoader(get()) }

}
