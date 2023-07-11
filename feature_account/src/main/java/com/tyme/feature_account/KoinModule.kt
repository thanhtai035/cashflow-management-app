package com.tyme.feature_account

import com.tyme.feature_account.data.dataModule
import com.tyme.feature_account.domain.domainModule
import com.tyme.feature_account.presentation.presentationModule

val featureAccountModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
