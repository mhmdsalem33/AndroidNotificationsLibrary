package com.salem.notifications.extentions

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun fetchAccessToken(
    context: Context,
    scopes: List<String>,
    onSuccess: (String) -> Unit,
    onFailure : (Exception) -> Unit,
    notificationServiceAccountJsonFile : Int,
    ) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val googleCredentials = getGoogleCredentials( context, scopes , notificationServiceAccountJsonFile)
            googleCredentials?.refreshIfExpired()
            val googleAccessToken = googleCredentials?.accessToken?.tokenValue ?: "empty token"
            withContext(Dispatchers.Main) {
                onSuccess(googleAccessToken)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                onFailure(e)
            }
        }
    }
}
