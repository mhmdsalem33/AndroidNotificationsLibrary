package com.salem.notifications.extentions

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import com.google.firebase.messaging.FirebaseMessaging

fun refreshDeviceToken(refreshedToken: (String) -> Unit, onFailure: (Exception) -> Unit) {
    FirebaseMessaging.getInstance().deleteToken()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { tokenTask ->
                    if (tokenTask.isSuccessful) {
                        val newToken = tokenTask.result
                        if (!newToken.isNullOrEmpty()) {
                            refreshedToken(newToken)
                        }
                    }
                }.addOnFailureListener {
                    onFailure(it)
                }
            }
        }.addOnFailureListener {
            onFailure(it)
        }
}

