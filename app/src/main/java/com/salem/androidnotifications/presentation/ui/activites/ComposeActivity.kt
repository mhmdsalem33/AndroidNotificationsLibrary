package com.salem.androidnotifications.presentation.ui.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.salem.androidnotifications.presentation.theme.AndroidNotificationsTheme
import com.salem.notifications.core.constants.NotificationConstants
import com.salem.notifications.data.repository.FcmRepositoryImpl
import com.salem.notifications.domian.models.fcm_request.FcmNotificationRequest
import com.salem.notifications.domian.models.fcm_request.Message
import com.salem.notifications.domian.use_case.SendFcmNotificationUseCase
import com.salem.notifications.notification_permission.RequestNotificationPermission
import com.salem.notifications.notification_sender.NotificationSender
import com.salem.notifications.retrofit_instance.RetrofitInstance
import com.salem.notifications.view_model.FcmViewModel
import com.salem.notifications.view_model.FcmViewModelFactory

class ComposeActivity : ComponentActivity() {

    private lateinit var fcmViewModel: FcmViewModel
    private val apiService = RetrofitInstance.apiService
    private val fcmRepository = FcmRepositoryImpl(apiService)
    private val sendFcmNotificationUseCase = SendFcmNotificationUseCase(fcmRepository)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            fcmViewModel = ViewModelProvider(
                this,
                FcmViewModelFactory(sendFcmNotificationUseCase)
            )[FcmViewModel::class.java]


            AndroidNotificationsTheme {


                // Check Notification Permission
                RequestNotificationPermission()


                //  set your channel id before create a notification channel
                NotificationConstants.CHANNEL_ID = "default_channel_id"


                //  createNotificationChannel  // required
                NotificationSender(this).createNotificationChannel(
                    channelName = NotificationConstants.CHANNEL_NAME,  // you can change it as you need
                    channelDescription = NotificationConstants.CHANNEL_DESCRIPTION // // you can change it as you need
                )



                sendNotificationToUser(
                    "get_your_access_token",
                    "get_your_device_token"
                )

            }
        }
    }


    private fun sendNotificationToUser(
        accessToken: String,
        deviceToken: String,
        notificationTitle: String? = null,  //  optional
        notificationBody: String? = null,  //  optional
        data: Map<String, String> = emptyMap(),
    ) {

        val message = Message(
            token = deviceToken,
            data = data,
//            notification = Notification(notificationTitle , notificationBody) // optional
        )

        val notificationRequest =
            FcmNotificationRequest(message = message)

        fcmViewModel.sendFcmNotification(
            accessToken = accessToken,
            projectId = "android-notifications-f597d",
            fcmNotificationRequest = notificationRequest
        )
    }

}






