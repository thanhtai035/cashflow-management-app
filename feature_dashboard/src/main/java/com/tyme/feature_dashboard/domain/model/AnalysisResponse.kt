package com.tyme.feature_dashboard.domain.model

import android.os.Parcelable

data class AnalysisResponse(
    val compliance: Int,
    val investment: Int,
    val saving: Int,
    val spending: Int,
    val string: String
)
