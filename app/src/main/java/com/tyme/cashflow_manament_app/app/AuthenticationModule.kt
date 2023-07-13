package com.tyme.cashflow_manament_app.app

import com.tyme.cashflow_manament_app.app.data.dataModule
import com.tyme.cashflow_manament_app.app.domain.domainModule
import com.tyme.cashflow_manament_app.app.presentation.presentationModule


val authenticationModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
