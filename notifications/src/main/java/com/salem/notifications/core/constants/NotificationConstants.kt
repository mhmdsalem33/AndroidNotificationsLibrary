package com.salem.notifications.core.constants

/**
 *  Mohamed Salem
 *  5/10/2024
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 */

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

object NotificationConstants {
    var CHANNEL_ID = "default_channel_id"
    var CHANNEL_NAME = "default_channel_name"
    var CHANNEL_DESCRIPTION = "default_channel_description"
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    var NOTIFICATION_PERMISSION = android.Manifest.permission.POST_NOTIFICATIONS
    var DEFAULT_NOTIFICATION_ID = 0
    var IMPORTANCE_HIGH = NotificationManager.IMPORTANCE_HIGH
}