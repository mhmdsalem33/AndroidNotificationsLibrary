package com.salem.notifications.extentions


/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging


fun subscribeTopic(topic : String, onSuccess : () -> Unit, onFailure : (Exception) -> Unit) {
    Firebase.messaging.subscribeToTopic(topic)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            }
        }.addOnFailureListener {
            onFailure(it)
        }
}
