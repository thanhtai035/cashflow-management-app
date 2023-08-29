package com.tyme.cashflow_manament_app.app.presentation.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyme.base_feature.common.Result
import com.tyme.cashflow_manament_app.app.domain.model.AuthenticationResponse
import com.tyme.cashflow_manament_app.app.domain.use_case.GetAuthenticationResponseUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthenticationViewModel (
    private val getAuthenticationResponseUseCase: GetAuthenticationResponseUseCase
) : ViewModel()
{
    val response: MutableLiveData<Result<AuthenticationResponse>> = MutableLiveData()

    // Get response from Log In post request
    public fun logIn(username: String, password: String) {
        getAuthenticationResponseUseCase(username, password).onEach { result ->
            response.postValue(result)
        }.launchIn(viewModelScope)
    }
}