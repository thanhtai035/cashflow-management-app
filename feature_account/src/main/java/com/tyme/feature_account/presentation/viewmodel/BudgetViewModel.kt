package com.tyme.feature_account.presentation.viewmodel

import androidx.lifecycle.*
import com.tyme.base.Common.Constant
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import com.tyme.base_feature.common.Result
import com.tyme.feature_account.domain.model.TransactionWeek
import com.tyme.feature_account.domain.use_case.GetTransactionDetailPageUseCase
import com.tyme.feature_account.domain.use_case.GetTransactionListUseCase
import java.text.DecimalFormat

class BudgetViewModel (
    private val getTransactionListUseCase: GetTransactionListUseCase,
) : ViewModel() {

    val transactionWeeks: MutableLiveData<Result<List<TransactionWeek>>> = MutableLiveData()

    fun initialize() {
        getTransactionWeeks(Constant.TEST_USER_ID)
    }


    private fun getTransactionWeeks(userID: String) {
        getTransactionListUseCase(userID).onEach { result ->
            transactionWeeks.postValue(result)
        }.launchIn(viewModelScope)
    }

    public fun getSummary(transactionWeeks: List<TransactionWeek>): TransactionSummary {
        var totalEntertainment = 0.0
        var totalFood = 0.0
        var totalInvestment = 0.0
        var totalOthers = 0.0
        var totalTravel = 0.0
        var totalUtilities = 0.0
        var totalOutcomes = 0.0

        transactionWeeks.forEach { transactionWeek ->
            totalEntertainment += transactionWeek.category.entertainmentAmount
            totalFood += transactionWeek.category.foodAmount
            totalInvestment += transactionWeek.category.investmentAmount
            totalOthers += transactionWeek.category.othersAmount
            totalTravel += transactionWeek.category.travelAmount
            totalUtilities += transactionWeek.category.utilitiesAmount
            totalOutcomes += transactionWeek.totalOutcome
        }
        return TransactionSummary(roundToOneDecimalPlace(totalEntertainment)*-1
            , roundToOneDecimalPlace(totalFood)*-1
            , roundToOneDecimalPlace(totalInvestment)*-1,
            roundToOneDecimalPlace(totalOthers) * -1,
            roundToOneDecimalPlace(totalTravel) * -1,
            roundToOneDecimalPlace(totalUtilities)*-1,
            roundToOneDecimalPlace(totalOutcomes))
    }
    fun roundToOneDecimalPlace(value: Double): Double {
        val decimalFormat = DecimalFormat("#.#")
        return decimalFormat.format(value).toDouble()
    }

}

    public data class TransactionSummary(
        var totalEntertainment: Double = 0.0,
        var totalFood: Double = 0.0,
        var totalInvestment: Double = 0.0,
        var totalOthers: Double = 0.0,
        var totalTravel: Double = 0.0,
        var totalUtilities: Double = 0.0,
        var totalOutcomes: Double = 0.0

    )
