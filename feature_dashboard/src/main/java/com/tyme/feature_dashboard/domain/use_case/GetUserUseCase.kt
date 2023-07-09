package com.tyme.feature_dashboard.domain.use_case

import com.tyme.feature_dashboard.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.tyme.base_feature.common.Result
import com.tyme.feature_dashboard.domain.model.TransactionWeek
import com.tyme.feature_dashboard.domain.model.User
import com.tyme.feature_dashboard.domain.repository.UserRepository

class GetUserUseCase (
    private val repository: UserRepository
){
    operator fun invoke(userID: String
    ): Flow<Result<User>> = flow {

        try {
            emit(Result.Loading<User>())
            val result = repository.getUser(userID)
            emit(Result.Success<User>(result))
        } catch(e: HttpException) {
            emit(Result.Error<User>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<User>(e.toString()))
        }
    }
}