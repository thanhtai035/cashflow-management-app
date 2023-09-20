package com.tyme.feature_notification.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.tyme.base_feature.common.Result
import com.tyme.feature_notification.domain.model.UserNotification
import com.tyme.feature_notification.domain.repository.NotificationRepository

class GetNotificationUseCase (
    private val repository: NotificationRepository
){
    operator fun invoke(userID: String, pageNum: Int
    ): Flow<Result<List<UserNotification>>> = flow {

        try {
            emit(Result.Loading<List<UserNotification>>())
            val result = repository.getNotifications(userID, pageNum)
            emit(Result.Success<List<UserNotification>>(result))
        } catch(e: HttpException) {
            emit(Result.Error<List<UserNotification>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<List<UserNotification>>(e.toString()))
        }
    }
}