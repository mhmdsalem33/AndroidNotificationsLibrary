package com.salem.androidnotifications.presentation.ui.activites

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.salem.androidnotifications.R
import com.salem.notifications.core.constants.NotificationConstants
import com.salem.notifications.domian.models.fcm_request.FcmNotificationRequest
import com.salem.notifications.domian.models.fcm_request.Message
import com.salem.notifications.notification_permission.requestNotificationPermission
import com.salem.notifications.data.repository.FcmRepositoryImpl
import com.salem.notifications.retrofit_instance.RetrofitInstance
import com.salem.notifications.domian.use_case.SendFcmNotificationUseCase
import com.salem.notifications.view_model.FcmViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.salem.notifications.extentions.deleteDeviceToken
import com.salem.notifications.extentions.fetchAccessToken
import com.salem.notifications.extentions.fetchGoogleCredentials
import com.salem.notifications.extentions.getDeviceToken
import com.salem.notifications.extentions.refreshDeviceToken
import com.salem.notifications.extentions.subscribeTopic
import com.salem.notifications.extentions.unSubscribeTopic
import com.salem.notifications.notification_sender.NotificationSender


class MainActivity : AppCompatActivity() {


    // 1 init the notification sender with your activity or fragment or compose activity
    private val notificationSender by lazy { NotificationSender(this) } // required


    // 2 init fcm view model if you will use the remote fcm   -> Optional

    private val fcmViewModel: FcmViewModel by lazy {
        val apiService = RetrofitInstance.apiService
        val fcmRepository = FcmRepositoryImpl(apiService)
        val sendFcmNotificationUseCase = SendFcmNotificationUseCase(fcmRepository)
        FcmViewModel(sendFcmNotificationUseCase)
    }


    private var deviceToken: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 3 Required
        checkNotificationPermission()

        // 4 set your channel id before create a notification channel
        NotificationConstants.CHANNEL_ID = "default_channel_id"


        // 5 createNotificationChannel  // required
        notificationSender.createNotificationChannel(
            channelName = NotificationConstants.CHANNEL_NAME,  // you can change it as you need
            channelDescription = NotificationConstants.CHANNEL_DESCRIPTION // // you can change it as you need
        )


        //Examples


//          //  Example 1 Send a basic notification
//        notificationSender.sendNotification(
//            notificationId = 1 ,
//            title   =   "Exclusive Discount From Salem!",  // optional
//            content =   "Buffalo Burger: Enjoy 50% off on all burgers today!", // optional
//            icon    =    R.drawable.ic_launcher_foreground,  // optional
//            bigIcon =    BitmapFactory.decodeResource(resources, R.drawable.ic_user), // optional
//            intent  =    Intent(this , MainActivity::class.java)  // optional
//        )


        // Example 2 Send a Expandable Notification with xml from local
//        notificationSender.showExpandableNotificationXml(
//            context = this,
//            title = "Expand Notification From Salem",
//            content = "Buffalo Burger: Enjoy 50% off on all burgers today!",
//            smallIcon = R.drawable.ic_launcher_foreground,
//            expandImage  = R.drawable.buffalo_burger_disccount_img,
//            intent = Intent(this , MainActivity::class.java),
//            notificationId = 1
//        )
//


        //Example 3 send  a Expandable Notification with xml and expand img from url
//        notificationSender.showExpandableNotificationXml(
//            context   = this,
//            title     = "\uD83D\uDD25 Exclusive Discount! \uD83D\uDD25",
//            content   = "Buffalo Burger: Enjoy 40% off on all burgers today!",
//            expandImage    = "https://buffalonlineorderingprod.s3.eu-west-1.amazonaws.com/offers/7d9d03be48a42c120831705d40646f23.png",
//            smallIcon = R.drawable.ic_launcher_foreground ,
//            notificationId = 1,
//            intent = intent
//        )


        //  4 - Update your notification with the same notification id
//        notificationSender.updateNotificationXml(
//            notificationId = 1 ,
//            title   =   "Exclusive Discount From Salem!",  // optional
//            content =   "Buffalo Burger: Enjoy 50% off on all burgers today!", // optional
//            icon    =    R.drawable.ic_launcher_foreground,  // optional
//            bigIcon =    BitmapFactory.decodeResource(resources, R.drawable.ic_user), // optional
//            intent  =    Intent(this , MainActivity::class.java)  // optional
//        )


        // 4 -Cancel your notification with the same notification id you have created notification with
//        notificationSender.cancelNotification(1)
//
//        //
//
//        // 5 -Cancel all your notifications
//        notificationSender.cancelAllNotifications()


        // 6 delete a notification channel with channel id
//        notificationSender.deleteNotificationChannel(  NotificationConstants.CHANNEL_ID )


        // 7  first get a Device token
        getTheDeviceToken()


        //  8- second get access token
//        getAccessToken()


//         9- delete Device Token
        //         refreshTheDeviceToken()


//         10 -> delete The Device Token
        //         deleteTheDeviceToken()


        // 11 - send a FCM notification to user
//        sendNotificationToUser(
//            accessToken = "get your access token",
//            deviceToken = "get your device token",
//            data =  mapOf(
//                "logo_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXMSWIWAlex9VThDh8XjpVd0noYacIuPgCmQ&s",
//                "expanded_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3OItiHanoLSPt_j9cMj7U1t_4Ft4_Zun2Kg&s"
//            ),
//        )


        // 12 Subscribe The Topic
//        subscribeTheTopic("Weather")


        // 13 un subscribe the Topic
//        unSubscribeTheTopic("Weather")


       // 14 -> fetch  Google credentials
//            fetchTheGoogleCredentials()


        // Get FCM Loading status
//        15 observeLoadingRemoteFcm()

        //  16 Get FCM Success status
//        observeSuccessRemoteFcm()


        // 17 Get FCM Error status
//        observeErrorRemoteFcm()





    }


    private fun checkNotificationPermission() {
        requestNotificationPermission { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "generated", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "not generated", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun getTheDeviceToken() {
        getDeviceToken(
            deviceToken = { token ->
                Log.e("testAccessToken", "device token  $token")
                deviceToken = token
            },
            onFailure = {
                Log.e("testAccessToken", "Error  token  $it")
            }
        )
    }


//    private fun getAccessToken() {
//
//        val scopes = listOf(
//            "https://www.googleapis.com/auth/firebase.messaging",
//            "https://www.googleapis.com/auth/cloud-platform"
//        )
//
//        fetchAccessToken(
//            this,
//            scopes,
//            notificationServiceAccountJsonFile = R.raw.notification_service_account, // get a file service account from firebase console -- project setting --  service accounts   -> choose java   and click on generate a new private key
//            onSuccess = { accessToken ->
//
//                // by getting access token you can send a FCM remote notification to some topic or to user
//
//
//                // Example  -> 1
//                // by send a notification to topic this mean all user have subscribed the topic will receive a notification
////                sendNotificationToTopic(
////                    topic = "Weather",
////                    accessToken = accessToken,
////                    data = mapOf(
////                        "logo_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXMSWIWAlex9VThDh8XjpVd0noYacIuPgCmQ&s",
////                        "expanded_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3OItiHanoLSPt_j9cMj7U1t_4Ft4_Zun2Kg&s"
////                    )
////                )
//
//
//                // Example 2 Send a FCM To User with access token and device token
////                sendNotificationToUser(
////                    accessToken  = accessToken,
////                    deviceToken  = deviceToken ?: "",
////                    data = mapOf(
////                        "logo_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3OItiHanoLSPt_j9cMj7U1t_4Ft4_Zun2Kg&s",
////                        "expanded_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3OItiHanoLSPt_j9cMj7U1t_4Ft4_Zun2Kg&s"
////                    )
////                )
//                Log.e("TestFcm", "Access token $accessToken")
//            },
//            onFailure = {
//                Log.e("TestFcm", it.toString())
//                Toast.makeText(this, "Failed to get Access token ${it.message}", Toast.LENGTH_SHORT)
//                    .show()
//            },
//        )
//
//    }


    private fun sendNotificationToUser(
        accessToken: String,
        deviceToken: String,
        notificationTitle: String? = null,  // optional
        notificationBody: String? = null,  // optional
        data: Map<String, String> = emptyMap(),
    ) {

        val message = Message(
            token = deviceToken,
            data = data,
//            notification = Notification(notificationTitle , notificationBody) // optional
        )

        val notificationRequest = FcmNotificationRequest(message = message)

        fcmViewModel.sendFcmNotification(
            accessToken = accessToken,
            projectId = "android-notifications-f597d", // Id of project get it from firebase console
            fcmNotificationRequest = notificationRequest
        )


    }

    private fun sendNotificationToTopic(
        accessToken: String,
        topic: String,
        notificationTitle: String? = null, // optional
        notificationBody: String? = null,  // optional
        data: Map<String, String> = emptyMap(),
    ) {

        val message = Message(
            topic = topic,
            data = data,
//            notification = Notification(notificationTitle , notificationBody) // optional
        )


        val notificationRequest =
            FcmNotificationRequest(message = message)

        fcmViewModel.sendFcmNotification(
            accessToken = accessToken,
            projectId = "android-notifications-f597d",   // Id of project get it from firebase console
            fcmNotificationRequest = notificationRequest
        )


    }


    private fun refreshTheDeviceToken() {
        refreshDeviceToken(
            refreshedToken = {
                Log.e("testAccessToken", "device token  $it")
            },
            onFailure = {
                Log.e("testAccessToken", "Error refresh Token  ${it.message}")
            }
        )
    }

    private fun deleteTheDeviceToken() {
        deleteDeviceToken(
            onSuccess = {
                Log.e("testAccessToken", "Device token deleted successfully")
            },
            onFailure = {
                Log.e(
                    "testAccessToken",
                    "Something went wrong with delete device token  ${it.message}"
                )
            }
        )
    }

    private fun subscribeTheTopic(topic: String) {
        subscribeTopic(
            topic, // Topic Name
            onSuccess = {
                Toast.makeText(this, "Subscribed", Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(this, "Subscribed failed ${it.message}", Toast.LENGTH_SHORT).show()
            })
    }

    private fun unSubscribeTheTopic(topic: String) {
        unSubscribeTopic(
            topic,
            onSuccess = {
                Toast.makeText(this, "Un Subscribed", Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(this, "Un Subscribed failed ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            })
    }


//    private fun fetchTheGoogleCredentials() {
//        val scopes = listOf(
//            "https://www.googleapis.com/auth/firebase.messaging",
//            "https://www.googleapis.com/auth/cloud-platform"
//        )
//
//        fetchGoogleCredentials(
//            context = this,
//            scopes = scopes,
//            notificationServiceAccountJsonFile = R.raw.notification_service_account, // get a file service account from firebase console -- project setting --  service accounts   -> choose java   and click on generate a new private key
//            onSuccess = { credentials ->
//                // Use the GoogleCredentials object as needed
//                Log.e("TestFCM", "credentials $credentials")
//            },
//            onFailure = { exception ->
//                // Handle the failure
//                Log.e("TestFCM", "error $exception")
//                println("Failed to retrieve GoogleCredentials: ${exception.message}")
//            }
//        )
//    }


    private fun observeLoadingRemoteFcm() {
        fcmViewModel.fcmLoading.onEach { loading ->
            if (loading) {
                Log.e("TestFcm", "Send Fcm Notification is loading")
            } else {
                Log.e("TestFcm", "Send Fcm Notification is stopped")
            }
        }.launchIn(lifecycleScope)

    }

    private fun observeSuccessRemoteFcm() {
        fcmViewModel.fcmResponse.onEach { fcmResponse ->
            fcmResponse?.let {
                Toast.makeText(
                    this,
                    "Notification sent successfully: ${it.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.launchIn(lifecycleScope)
    }


    private fun observeErrorRemoteFcm() {
        fcmViewModel.error.onEach { fcmError ->
            fcmError?.let {
                Log.e("TestFcm", "Error to send a fcm notification $fcmError")
            }
        }.launchIn(lifecycleScope)


    }

}











