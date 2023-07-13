package com.tyme.cashflow_manament_app.app.domain.use_case

import android.util.Log
import com.tyme.cashflow_manament_app.app.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.tyme.base_feature.common.Result
import com.tyme.cashflow_manament_app.app.domain.model.AuthenticationResponse
import retrofit2.Call

class GetAuthenticationResponseUseCase (
    private val repository: AuthenticationRepository
){
    operator fun invoke(username: String, password: String): Flow<Result<AuthenticationResponse>> = flow {
        try {
            emit(Result.Loading<AuthenticationResponse>())
            val result = repository.getAuthenticationResponse(username,password)
            emit(Result.Success<AuthenticationResponse>(result))
        } catch(e: HttpException) {
            Log.d("tai", e.toString())
            emit(Result.Error<AuthenticationResponse>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            Log.d("tai", e.toString())
            emit(Result.Error<AuthenticationResponse>("Couldn't reach server. Check your internet connection."))
        }
    }

}