package com.salem.androidnotifications.presentation.ui.activites

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import com.salem.androidnotifications.R
import com.salem.androidnotifications.bubble_notification.BubbleService
import com.salem.androidnotifications.bubble_notification.showConversationNotification
import com.salem.notifications.core.constants.NotificationConstants

class ShortCutActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.N_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_cut)


        if (Settings.canDrawOverlays(this)) {
            if (!isServiceRunning(BubbleService::class.java)) {
                val intent = Intent(this, BubbleService::class.java)
                startService(intent)
            } else {
                Log.d("BubbleService", "Service is already running!")
            }
        } else {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
        }


        showConversationNotification(this)


    }



    private fun isServiceRunning(serviceClass: Class<out Service>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }


}