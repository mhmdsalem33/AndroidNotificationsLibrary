package com.salem.notifications.extentions


/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */


import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.*

fun getGoogleCredentials(
    context: Context,
    scopes: List<String>,
    notificationServiceAccountJsonFile : Int
): GoogleCredentials? {
    val inputStream = context.resources.openRawResource(notificationServiceAccountJsonFile)
    return GoogleCredentials.fromStream(inputStream).createScoped(scopes)
}

fun fetchGoogleCredentials(
    context: Context,
    scopes: List<String>,
    onSuccess: (GoogleCredentials) -> Unit,
    onFailure: (Exception) -> Unit,
    notificationServiceAccountJsonFile: Int
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val googleCredentials = getGoogleCredentials(context, scopes, notificationServiceAccountJsonFile)

            withContext(Dispatchers.Main) {
                if (googleCredentials != null) {
                    onSuccess(googleCredentials)
                } else {
                    onFailure(Exception("GoogleCredentials could not be created"))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                onFailure(e)
            }
        }
    }
}
