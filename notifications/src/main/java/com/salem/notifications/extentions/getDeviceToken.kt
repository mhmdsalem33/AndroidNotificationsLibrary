package com.salem.notifications.extentions

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import com.google.firebase.messaging.FirebaseMessaging


fun getDeviceToken(deviceToken: (String) -> Unit , onFailure : (String) -> Unit ) {
    FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
        if (!token.isNullOrEmpty()) {
            deviceToken(token)
        }
    }.addOnFailureListener{exceptionMessage ->
        if (!exceptionMessage.message.isNullOrEmpty()){
            onFailure(exceptionMessage.message ?: "some thing went wrong!")
        }
    }
}