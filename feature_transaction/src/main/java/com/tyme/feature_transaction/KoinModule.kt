package com.tyme.feature_transaction

import com.tyme.feature_transaction.data.dataModule
import com.tyme.feature_transaction.domain.domainModule
import com.tyme.feature_transaction.presentation.presentationModule

val featureTransactionModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
