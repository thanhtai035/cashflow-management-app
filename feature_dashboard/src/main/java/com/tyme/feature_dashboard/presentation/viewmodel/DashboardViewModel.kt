package com.tyme.feature_dashboard.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyme.base_feature.common.Constant
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import kotlinx.coroutines.flow.launchIn
import com.tyme.base_feature.common.Result
import com.tyme.feature_dashboard.domain.model.TransactionWeek
import com.tyme.feature_dashboard.domain.model.User
import com.tyme.feature_dashboard.domain.use_case.GetTransactionListUseCase
import com.tyme.feature_dashboard.domain.use_case.GetUserUseCase

class DashboardViewModel (
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val getUserUseCase: GetUserUseCase

) : ViewModel() {

    val _transaction: MutableLiveData<Result<List<TransactionWeek>>> = MutableLiveData()
    val _user: MutableLiveData<Result<User>> = MutableLiveData()

    fun initialize() {
        getUser(Constant.TEST_USER_ID)
        //getTransaction(Constant.TEST_USER_ID)
    }

    private fun getUser(userID: String) {
        getUserUseCase(userID).onEach { result ->
            _user.postValue(result)
        }.launchIn(viewModelScope)
    }

    private fun getTransaction(userID: String) {
        getTransactionListUseCase(userID).onEach { result ->
            _transaction.postValue(result)
        }.launchIn(viewModelScope)
    }
}



