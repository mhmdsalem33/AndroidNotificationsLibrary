package com.salem.notifications.notification_permission

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */


import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import androidx.fragment.app.Fragment

fun AppCompatActivity.requestNotificationPermission(callback: (Boolean) -> Unit) {
    // Only request permission on Android 13 (API 33) or above
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                callback(isGranted) // Pass whether permission was granted to the callback
            }

        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if it’s not granted
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            // Permission is already granted
            callback(true)
        }
    } else {
        // For Android versions below 13, no permission is needed
        callback(true)
    }
}

fun Fragment.requestNotificationPermission(callback: (Boolean) -> Unit) {
    // Only request permission on Android 13 (API 33) or above
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                callback(isGranted) // Pass whether permission was granted to the callback
            }
        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if it’s not granted
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            // Permission is already granted
            callback(true)
        }
    } else {
        // For Android versions below 13, no permission is needed
        callback(true)
    }
}
