package com.tyme.feature_dashboard

import com.tyme.feature_dashboard.data.dataModule
import com.tyme.feature_dashboard.domain.domainModule
import com.tyme.feature_dashboard.presentation.presentationModule

val featureDashboardModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
