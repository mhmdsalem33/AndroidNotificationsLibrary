package com.salem.androidnotifications.bubble_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import com.salem.androidnotifications.R
import com.salem.androidnotifications.presentation.ui.activites.ShortCutActivity

fun showConversationNotification(context: Context) {
    // Create the notification channel
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "conversation_channel",
            "Conversations",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications for chat messages"
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    // Create a person representing the sender
    val sender = Person.Builder()
        .setName("Jimmy")
        .setIcon(IconCompat.createWithResource(context, R.drawable.ic_user)) // Replace with a drawable icon
        .build()

    // MessagingStyle for the notification
    val messagingStyle = NotificationCompat.MessagingStyle(sender)
        .setConversationTitle("Messages")
        .addMessage("Hi there!"            , System.currentTimeMillis() , sender )
        .addMessage("How are you?"         , System.currentTimeMillis() , sender )
        .addMessage("Every thing is okay?" , System.currentTimeMillis() , sender )

    // PendingIntent to open the chat activity
    val intent = Intent(context, ShortCutActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Build the notification
    val notification = NotificationCompat.Builder(context, "conversation_channel")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setStyle(messagingStyle)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    // Show the notification
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(1001, notification)
}
