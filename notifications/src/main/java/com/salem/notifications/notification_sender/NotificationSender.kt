package com.salem.notifications.notification_sender


/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.salem.notifications.core.constants.NotificationConstants
import com.salem.notifications.extentions.bitmapFromResource

class NotificationSender(
    private val context: Context,
) {

    /**
    Through notificationManager we can make
    1- create notification channel
    2- show a notification
    3- update a notification
    4- cancel a notification
    5- cancel all notifications
    6- delete notification channel
     **/

    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    /**
    NotificationManager.IMPORTANCE_HIGH = 4
    NotificationManager.IMPORTANCE_MAX = 5
    NotificationManager.IMPORTANCE_LOW  = 2
    NotificationManager.IMPORTANCE_DEFAULT = 3
     **/

    /** Create the notification channel if Android version is 8.0 (Oreo) or above  **/
    /** Notification channels are required starting from Android 8.0 (API 26) **/


    fun createNotificationChannel(
        channelName: String,
        channelDescription: String,
        notificationManagerImportanceLevel: Int = NotificationManager.IMPORTANCE_HIGH
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = NotificationConstants.CHANNEL_ID
            val notificationChannel =
                NotificationChannel(channelID, channelName, notificationManagerImportanceLevel)
            notificationChannel.description = channelDescription
            try {
                notificationManager.createNotificationChannel(notificationChannel)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NotificationSender", "Error creating notification channel: ${e.message}")
            }
        }
    }


    /**
    Send a notification that works on all Android versions.
     */

    fun sendNotification(
        notificationId: Int = 0,
        title: String? = null,
        content: String? = null,
        icon: Int? = null,
        intent: Intent? = null,
        setCustomContentView: RemoteViews? = null,
        setCustomBigContentView: RemoteViews? = null,
        bigIcon: Bitmap? = null,
        pendingIntentFlags: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        setPriority: Int? = NotificationCompat.PRIORITY_MAX,
        autoCancel: Boolean = true
    ) {


        val defaultIntent =
            intent ?: context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent = getPendingIntent(context, intent ?: defaultIntent, pendingIntentFlags)


        val notificationBuilder =
            NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
                .setSmallIcon(
                    icon ?: 2131165355
                )
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setAutoCancel(autoCancel)
                .setPriority(
                    setPriority ?: NotificationCompat.PRIORITY_HIGH
                )
                .setFullScreenIntent(pendingIntent, true)

        // Apply the custom view if provided
        setCustomContentView?.let {
            notificationBuilder.setCustomContentView(it)
            notificationBuilder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
        }



        setCustomBigContentView?.let {
            notificationBuilder.setCustomBigContentView(setCustomBigContentView) // for expanded layout
        }

        bigIcon?.let {
            notificationBuilder.setLargeIcon(it)
        }

        try {
            notificationManager.notify(notificationId, notificationBuilder.build())
            Log.d("NotificationSender", "Notification sent with custom view.")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NotificationSender", "Error showing notification: ${e.message}")
        }
    }


    fun showExpandableNotificationXml(
        context: Context,
        title: String,
        content: String,
        expandImage: String,
        smallIcon: Int,
        notificationId: Int,
        intent: Intent?,
        pendingIntentFlags: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,

        ) {


        val defaultIntent =
            intent ?: context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent = getPendingIntent(context, intent ?: defaultIntent, pendingIntentFlags)

        // Load the image asynchronously with Glide
        Glide.with(context)
            .asBitmap()
            .load(expandImage)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Create the BigPictureStyle notification
                    val bigPictureStyle = NotificationCompat.BigPictureStyle()
                        .bigPicture(resource)

                    val notification =
                        NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
                            .setSmallIcon(smallIcon)
                            .setContentTitle(title)
                            .setContentText(content)
                            .setStyle(bigPictureStyle)
                            .setContentIntent(pendingIntent)
                            .build()

                    // Check for POST_NOTIFICATIONS permission on Android 13+
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(
                            context,
                            "Please check your notification permission",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return
                    }

                    NotificationManagerCompat.from(context).notify(notificationId, notification)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }


    fun showExpandableNotificationXml(
        context: Context,
        title: String,
        content: String,
        expandImage: Int,
        smallIcon: Int,
        notificationId: Int,
        intent: Intent?,
        pendingIntentFlags: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    ) {


        val defaultIntent =
            intent ?: context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent = getPendingIntent(context, intent ?: defaultIntent, pendingIntentFlags)


        // Load the default image from resources as a Bitmap
        val defaultBitmap = BitmapFactory.decodeResource(context.resources, expandImage)

        // Create the BigPictureStyle notification with the default image
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(defaultBitmap)

        val notification = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(bigPictureStyle)
            .setContentIntent(pendingIntent)
            .build()

        // Check for POST_NOTIFICATIONS permission on Android 13+
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Please check your notification permission", Toast.LENGTH_SHORT)
                .show()
            return
        }
        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }


    fun updateNotificationXml(
        notificationId: Int = 0,
        title: String? = null,
        content: String? = null,
        icon: Int? = null,
        intent: Intent? = null,
        setCustomContentView: RemoteViews? = null,
        setCustomBigContentView: RemoteViews? = null,
        bigIcon: Bitmap? = null,
        pendingIntentFlags: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        setPriority: Int? = NotificationCompat.PRIORITY_MAX,
        autoCancel: Boolean = true
    ) {
        sendNotification(
            notificationId          = notificationId,
            title                   = title,
            content                 = content,
            icon                    = icon,
            setCustomContentView    = setCustomContentView,
            intent                  = intent,
            setPriority             = setPriority,
            setCustomBigContentView = setCustomBigContentView,
            bigIcon                 = bigIcon,
            pendingIntentFlags      = pendingIntentFlags,
            autoCancel              = autoCancel
        )
    }


    fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }


    // Cancels all notifications
    fun cancelAllNotifications() {
        notificationManager.cancelAll()
    }


    fun deleteNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.deleteNotificationChannel(channelId)
        }
    }


    // for jetpack compose
    fun showBasicNotificationJetpack(
        title: String,
        content: String,
        icon: Int? = null,
        intent: Intent? = null,
        pendingIntentFlags: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        setPriority: Int? = NotificationCompat.PRIORITY_HIGH,
        autoCancel: Boolean = true,
        notificationId: Int

    ) {

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, pendingIntentFlags
        )

        val notification = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(icon ?: 2131165355)
            .setPriority(setPriority ?: NotificationManager.IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(autoCancel)
            .build()

        notificationManager.notify(
            notificationId,
            notification
        )
    }


    // for jetpack compose
    fun showExpandableNotificationJetpack(
        notificationId: Int = 0,
        title: String,
        content: String,
        icon: Int? = null,
        intent: Intent? = null,
        pendingIntentFlags: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        setPriority: Int? = NotificationCompat.PRIORITY_HIGH,
        imageExpandable: Int
    ) {

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, pendingIntentFlags
        )

        val notification = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(icon ?: 2131165355)
            .setPriority(setPriority ?: NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(
                        context.bitmapFromResource(
                            imageExpandable
                        )
                    )
            )
            .build()
        notificationManager.notify(notificationId, notification)
    }

}


private fun getPendingIntent(
    context: Context,
    intent: Intent?,
    pendingIntentFlags: Int
): PendingIntent {
    // If intent is null, use the main launcher activity as default
    val defaultIntent =
        intent ?: context.packageManager.getLaunchIntentForPackage(context.packageName)
    return PendingIntent.getActivity(
        context,
        0,
        defaultIntent,
        pendingIntentFlags
    )
}



