package com.tyme.feature_dashboard.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyme.base.Common.Constant
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import com.tyme.base_feature.common.Result
import com.tyme.feature_dashboard.domain.model.*
import com.tyme.feature_dashboard.domain.use_case.GetAnalysisUseCase
import com.tyme.feature_dashboard.domain.use_case.GetTransactionDetailPageUseCase
import com.tyme.feature_dashboard.domain.use_case.GetTransactionListUseCase
import com.tyme.feature_dashboard.domain.use_case.GetUserUseCase
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

class DashboardViewModel (
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAnalysisUseCase: GetAnalysisUseCase,
    private val getTransactionDetailPageUseCase: GetTransactionDetailPageUseCase

) : ViewModel() {

    val _transaction: MutableLiveData<Result<List<TransactionWeek>>> = MutableLiveData()
    val transactionPage: MutableLiveData<Result<List<TransactionDetail>>> = MutableLiveData()
    val _user: MutableLiveData<Result<User>> = MutableLiveData()
    val _analysis: MutableLiveData<Result<AnalysisResponse>> = MutableLiveData()

    fun initialize() {
        getUser(Constant.TEST_USER_ID)
        getTransaction(Constant.TEST_USER_ID)
        getTransactionPage(Constant.TEST_USER_ID)
        getAnalysis(Constant.TEST_USER_ID)
    }

    private fun getUser(userID: String) {
        getUserUseCase(userID).onEach { result ->
            _user.postValue(result)
        }.launchIn(viewModelScope)
    }
    fun getAnalysis(userID: String) {
        getAnalysisUseCase(userID).onEach { result ->
            _analysis.postValue(result)
        }.launchIn(viewModelScope)
    }

    private fun getTransaction(userID: String) {
        getTransactionListUseCase(userID).onEach { result ->
            _transaction.postValue(result)
        }.launchIn(viewModelScope)
    }

    private fun getTransactionPage(userID: String) {
        getTransactionDetailPageUseCase(userID).onEach { result ->
            transactionPage.postValue(result)
        }.launchIn(viewModelScope)
    }
}



