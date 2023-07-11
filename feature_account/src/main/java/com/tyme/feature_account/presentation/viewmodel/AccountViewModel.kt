package com.tyme.feature_account.presentation.viewmodel

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.tyme.base_feature.common.Constant
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import com.tyme.base_feature.common.Result
import com.tyme.feature_account.domain.model.TransactionDetailPage
import com.tyme.feature_account.domain.model.TransactionWeek
import com.tyme.feature_account.domain.use_case.GetTransactionDetailPageUseCase
import com.tyme.feature_account.domain.use_case.GetTransactionListUseCase
import com.tyme.feature_account.presentation.util.CategoryEnum
import com.tyme.feature_account.presentation.util.CategoryEnum.*
import com.tyme.feature_history.R
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AccountViewModel (
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val getTransactionDetailPageUseCase: GetTransactionDetailPageUseCase,
) : ViewModel() {

    val transactionPage: MutableLiveData<Result<TransactionDetailPage>> = MutableLiveData()
    val transactionWeeks: MutableLiveData<Result<List<TransactionWeek>>> = MutableLiveData()
    private val _state: MutableLiveData<TransactionState> = MutableLiveData<TransactionState>().apply {
        value = TransactionState()
    }
    val state: LiveData<TransactionState> = _state

    fun initialize() {
        getTransactionPage(Constant.TEST_USER_ID)
        getTransactionWeeks(Constant.TEST_USER_ID)
    }


    public fun getTransactionPage(userID: String) {
        Log.d("tai", "categ")
        getTransactionDetailPageUseCase(userID, _state.value?.pageNum?:1,
                                          _state.value?.month?:LocalDateTime.now().monthValue,
                                            _state.value?.year?:LocalDateTime.now().year,
                                                 _state.value?.sortType, _state.value?.sortDir,
                                                 _state.value?.category, _state.value?.keyword).onEach { result ->
            transactionPage.postValue(result)
        }.launchIn(viewModelScope)
    }

    private fun getTransactionWeeks(userID: String) {
        getTransactionListUseCase(userID).onEach { result ->
            transactionWeeks.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun updateState(newState: TransactionState) {
        _state.value = newState
    }

    fun performActionUsingState() {
        val currentState = state.value

        viewModelScope.launch {
            getTransactionWeeks(Constant.TEST_USER_ID)
        }
    }

    public fun getSummary(transactionWeeks: List<TransactionWeek>): TransactionSummary {
        val month: Int = _state.value?.month?:LocalDateTime.now().monthValue
        val year: Int = _state.value?.year?:LocalDateTime.now().year
        val filteredTransactions = transactionWeeks.filter { it.month == month && it.year == year }

        var totalIncome = 0.0
        var totalOutcome = 0.0

        var totalEntertainment = 0f
        var totalFood = 0f
        var totalInvestment = 0f
        var totalOthers = 0f
        var totalTravel = 0f
        var totalUtilities = 0f

        filteredTransactions.forEach { transactionWeek ->
            totalIncome += transactionWeek.totalIncome
            totalOutcome += transactionWeek.totalOutcome
            totalEntertainment += transactionWeek.category.entertainmentAmount
            totalFood += transactionWeek.category.foodAmount
            totalInvestment += transactionWeek.category.investmentAmount
            totalOthers += transactionWeek.category.othersAmount
            totalTravel += transactionWeek.category.travelAmount
            totalUtilities += transactionWeek.category.utilitiesAmount
        }
        val categoryList: Array<Float> = arrayOf(totalEntertainment, totalFood, totalInvestment, totalOthers, totalTravel, totalUtilities)
        val titleOrders: Array<CategoryEnum> = arrayOf(Entertainment, Food, Investment, Others, Travel, Utilities)
        @ColorRes val colors: Array<Int> = arrayOf(R.color.black, R.color.green, R.color.light_white, R.color.light_yellow, R.color.teal_700, R.color.purple_500)
        return TransactionSummary(totalIncome, totalOutcome, categoryList, titleOrders, colors)
    }

    fun observeState(owner: LifecycleOwner, observer: Observer<TransactionState>) {
        state.observe(owner, observer)
    }

    // Setter methods to update individual attributes of the state
    fun setPageNum(pageNum: Int) {
        val currentState = state.value ?: return
        _state.postValue(currentState.copy(pageNum = pageNum))
    }

//    fun setMonth(month: Int) {
//        val currentState = state.value ?: return
//        state.value = currentState.copy(month = month)
//    }
//
//    fun setYear(year: Int) {
//        val currentState = state.value ?: return
//        state.value = currentState.copy(year = year)
//    }
//
//    fun setSortDir(sortDir: String?) {
//        val currentState = state.value ?: return
//        state.value = currentState.copy(sortDir = sortDir)
//    }
//
//    fun setSortType(sortType: String?) {
//        val currentState = state.value ?: return
//        state.value = currentState.copy(sortType = sortType)
//    }

    public fun setCategory(category: String?) {
        val currentState = state.value ?: return
        _state.postValue(currentState.copy(category = category))
    }

//    fun setKeyword(keyword: String?) {
//        val currentState = state.value ?: return
//        state.value = currentState.copy(keyword = keyword)
//    }

}

    public data class TransactionSummary (
        val totalIncome: Double = 0.0,
        val totalOutcome: Double = 0.0,
        val categoryDistribution : Array<Float> ,
        val categoryTitleList: Array<CategoryEnum>,
        @ColorRes val color: Array<Int>
    )
