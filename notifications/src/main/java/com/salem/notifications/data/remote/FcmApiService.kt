package com.salem.notifications.data.remote// FcmApiService.kt
import com.salem.notifications.domian.models.FcmResponse
import com.salem.notifications.domian.models.fcm_request.FcmNotificationRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *  Mohamed Salem
 *  5/10/2024
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */
interface FcmApiService {

    @Headers("Content-Type: application/json")
    @POST("v1/projects/{project_id}/messages:send")
    suspend fun sendNotification(
        @Header("Authorization") accessToken : String,
        @Path("project_id") projectId : String ,
        @Body fcmRequest: FcmNotificationRequest
    ): Response<FcmResponse>

}
