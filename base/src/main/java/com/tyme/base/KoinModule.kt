package com.tyme.base

import com.tyme.base.presentation.navigation.NavManager
import org.koin.dsl.module

val baseModule = module {
    single { NavManager() }
}
