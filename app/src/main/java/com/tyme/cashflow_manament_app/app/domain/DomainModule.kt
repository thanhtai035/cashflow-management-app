package com.tyme.cashflow_manament_app.app.domain


import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.tyme.cashflow_manament_app.app.domain.use_case.*
internal val domainModule = module {

    singleOf(::GetAuthenticationResponseUseCase)


}
