package com.salem.notifications.domian.repository


/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import com.salem.notifications.domian.models.FcmResponse
import com.salem.notifications.domian.models.fcm_request.FcmNotificationRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FcmRepository {
    suspend fun sendNotification( accessToken : String ,  projectId : String , fcmNotificationRequest : FcmNotificationRequest)  :Flow<Response<FcmResponse>>
}