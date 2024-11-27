package com.salem.androidnotifications.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.salem.androidnotifications.CustomNotificationClass


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        // 1-  Get the FCM Send by the notification
        remoteMessage.notification?.let {

            val notificationTitle = it.title
            val notificationBody = it.body


        }

        // 2-  Get the FCM Send by the Data
        remoteMessage.data.isNotEmpty().let {
            val logoImage = remoteMessage.data["logo_image"] ?: "empty logo image"
            val expandedImage = remoteMessage.data["expanded_image"] ?: "empty expanded image"

            CustomNotificationClass(this ).sendCustomNotification(
                logoImage = logoImage,
                expandImage = expandedImage
            )

            Log.e("testFCM", "Remote logo image is $logoImage")
            Log.e("testFCM", "Remote expanded image is $expandedImage")


        }
    }


}