package com.salem.notifications.domian.use_case
/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */


import com.salem.notifications.domian.models.FcmResponse
import com.salem.notifications.domian.models.fcm_request.FcmNotificationRequest
import com.salem.notifications.domian.repository.FcmRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class SendFcmNotificationUseCase(private val fcmRepository: FcmRepository) {
    suspend operator fun invoke(
        accessToken : String ,
        projectId: String,
        fcmNotificationRequest: FcmNotificationRequest
    ): Flow<Response<FcmResponse>> =
        fcmRepository.sendNotification(
            accessToken = accessToken ,
            projectId   = projectId ,
            fcmNotificationRequest = fcmNotificationRequest
        )
}