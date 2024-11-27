package com.salem.notifications.data.repository

import com.salem.notifications.domian.models.FcmResponse
import com.salem.notifications.domian.models.fcm_request.FcmNotificationRequest
import com.salem.notifications.data.remote.FcmApiService
import com.salem.notifications.domian.repository.FcmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

class FcmRepositoryImpl(private val fcmApiService: FcmApiService) : FcmRepository {

    override suspend fun sendNotification(
        accessToken : String ,
        projectId: String,
        fcmNotificationRequest: FcmNotificationRequest
    ): Flow<Response<FcmResponse>> = flow {
        val response = fcmApiService.sendNotification(accessToken  = "Bearer $accessToken", projectId = projectId , fcmRequest = fcmNotificationRequest)
        emit(response)
    }
}