package com.salem.notifications.extentions

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import com.google.firebase.messaging.FirebaseMessaging

fun deleteDeviceToken(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    FirebaseMessaging.getInstance().deleteToken()
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}
