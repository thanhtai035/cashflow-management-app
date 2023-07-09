package com.tyme.feature_history

import com.tyme.feature_history.data.dataModule
import com.tyme.feature_history.domain.domainModule
import com.tyme.feature_history.presentation.presentationModule


val featureHistoryModules = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
