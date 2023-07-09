package com.tyme.feature_history.presentation.TransactionHistory

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyme.base_feature.common.Constant
import com.tyme.transaction_feature.domain.use_case.GetTransactionListPageUseCase
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import com.tyme.transaction_feature.domain.model.TransactionDetailPage
import kotlinx.coroutines.flow.launchIn
import com.tyme.base_feature.common.Result

class TransactionDetailViewModel (
    private val transactionDetailPageUseCase: GetTransactionListPageUseCase
) : ViewModel() {

    private val _state = mutableStateOf(TransactionDetailState())
    val state : State<TransactionDetailState> = _state

    fun initializeViewModel() {
        Log.d("Test","ViewModel On Create")
        getTransactionDetailPage(Constant.TEST_USER_ID, state.value.pageNum, state.value.month, state.value.year, null, null, null, null)
    }

    private fun getTransactionDetailPage(userID: String, pageNum: Int, month: Int, year: Int,
                                         sortType: String?, sortDir : String?,
                                         category: String?, keyword: String?) {
        transactionDetailPageUseCase(userID, pageNum, month, year, sortType, sortDir, category, keyword).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = TransactionDetailState(transactionDetailPage = result.data)
                }
                is Result.Error -> {
                    _state.value = TransactionDetailState(message = result.message?: "Unexpected error")
                }
                is Result.Loading -> {
                    _state.value = TransactionDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class TransactionDetailState(
    val isLoading: Boolean = false,
    val message: String = "",

    val pageNum: Int = 1,
    val month: Int = LocalDateTime.now().monthValue,
    val year: Int = LocalDateTime.now().year,
    val sortType: String? = null,
    val sortDir : String? = null,
    val category: String? = null,
    val keyword: String? = null,

    val transactionDetailPage: TransactionDetailPage? = null
)