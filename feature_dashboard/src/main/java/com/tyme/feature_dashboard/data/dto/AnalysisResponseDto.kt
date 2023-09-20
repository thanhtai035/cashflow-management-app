package com.tyme.feature_dashboard.data.dto

import com.tyme.feature_dashboard.domain.model.AnalysisResponse
import com.tyme.feature_dashboard.domain.model.Category

@kotlinx.serialization.Serializable
data class AnalysisResponseDto(
    val compliance: Int,
    val investment: Int,
    val saving: Int,
    val spending: Int,
    val string: String
)
fun AnalysisResponseDto.toEntity(): AnalysisResponse {
    return AnalysisResponse(
        compliance = compliance,
        investment = investment,
        saving = saving,
        spending = spending,
        string = string
    )
}
