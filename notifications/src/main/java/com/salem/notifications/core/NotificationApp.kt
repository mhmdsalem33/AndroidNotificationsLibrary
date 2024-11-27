package com.salem.notifications.core

/**
 *  Mohamed Salem
 *  5/10/2024
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 */

import android.app.Application
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class NotificationApp  : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}