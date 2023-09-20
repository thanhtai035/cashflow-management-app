package com.tyme.feature_notification.presentation.viewmodel

import androidx.lifecycle.*
import com.tyme.base.Common.Constant
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import com.tyme.base_feature.common.Result
import com.tyme.feature_notification.domain.model.UserNotification
import com.tyme.feature_notification.domain.use_case.GetNotificationUseCase
import kotlinx.coroutines.launch

class NotificationViewModel (
    private val getNotificationUseCase: GetNotificationUseCase
) : ViewModel() {

    val notificationPage: MutableLiveData<Result<List<UserNotification>>> = MutableLiveData()
    private val _state: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = 1
    }
    val state: LiveData<Int> = _state

    fun initialize() {
        getNotification(Constant.TEST_USER_ID)
    }


    private fun getNotification(userID: String) {
        getNotificationUseCase(userID, _state.value ?: 1).onEach { result ->

            notificationPage.postValue(result)
        }.launchIn(viewModelScope)
    }

    public fun updateState() {
        _state.value = _state.value?.plus(1)
        viewModelScope.launch {
            getNotification(Constant.TEST_USER_ID)
        }
    }

}
