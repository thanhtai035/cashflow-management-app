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

    private val _state = mutableStateOf(DashboardState())
    val _transaction: MutableLiveData<Result<List<TransactionWeek>>> = MutableLiveData()
    val _user: MutableLiveData<Result<User>> = MutableLiveData()

    val state : State<DashboardState> = _state

    fun initialize() {
        getUser(Constant.TEST_USER_ID)
        getTransactionMonth(Constant.TEST_USER_ID)
    }

    private fun getUser(userID: String) {
        getUserUseCase(userID).onEach { result ->
            _user.postValue(result)
//            when (result) {
//                is Result.Success -> {
//                    _user.postValue(Result.Success(result.data))
//                }
//                is Result.Error -> {
//                    _user.postValue(Result.Error(result.message?: "Unexpected Error"))
//                }
//                is Result.Loading -> {
//                    _user.postValue(Result.Success(result.data))
//                }
//            }
        }.launchIn(viewModelScope)
    }

    private fun getTransactionMonth(userID: String) {
        getTransactionListUseCase(userID).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = DashboardState(transactionList = result.data)
                }
                is Result.Error -> {
                    _state.value = DashboardState(message = result.message?: "Unexpected error")
                }
                is Result.Loading -> {
                    _state.value = DashboardState(transactionLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class DashboardState(
    val transactionLoading: Boolean = false,
    val userLoading: Boolean = false,

    val message: String = "",
    val user: User? = null,
    val transactionList: List<TransactionWeek>? = null
)