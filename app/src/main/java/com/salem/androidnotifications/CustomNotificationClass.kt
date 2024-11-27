package com.salem.androidnotifications

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.salem.notifications.extentions.loadImageFromUrl
import com.salem.notifications.notification_sender.NotificationSender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class CustomNotificationClass(private val context: Context) {


    fun sendCustomNotification(logoImage: String, expandImage: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val bitmapLogoImage       = async { loadImageFromUrl(logoImage) }.await()   // loadImageFromUrl fun  will convert it to BitMap
            val bitmapExpandedImage   = async { loadImageFromUrl(expandImage) }.await()

            val customView = RemoteViews( context.packageName, R.layout.notification_layout ).apply {
                setTextViewText(R.id.title, "\uD83D\uDD25 Exclusive Discount!")
                setTextViewText(R.id.message, "Buffalo Burger: Enjoy 50% off on all burgers today!")
                setImageViewBitmap(R.id.custom_notification_icon, bitmapLogoImage)
            }

            val notificationLayoutExpanded =
                RemoteViews(context.packageName, R.layout.notification_layout_two).apply {
                    setTextViewText(R.id.title, "\uD83C\uDF54 Buffalo Burger Discount!")
                    setTextViewText(
                        R.id.message,
                        "Treat yourself to a delicious meal! Get 50% off all burgers today only. Don't miss this mouth-watering deal!"
                    )
                    setImageViewBitmap(
                        R.id.custom_notification_icon,
                        bitmapLogoImage
                    )
                    setImageViewBitmap(R.id.img_expand, bitmapExpandedImage)
                }


            val icon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                R.drawable.ic_launcher_foreground
            } else {
                R.mipmap.ic_launcher
            }

            Log.e("testCustom" , "Called")

            NotificationSender(context).sendNotification(
                notificationId = 1,
                icon = icon,
                setPriority = NotificationCompat.PRIORITY_HIGH,
                setCustomContentView = customView,
                setCustomBigContentView = notificationLayoutExpanded
            )
        }
    }

}
