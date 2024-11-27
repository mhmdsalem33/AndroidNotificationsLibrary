package com.salem.notifications.extentions

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

fun loadImageFromUrl(url: String): Bitmap? {
    if (!url.isValidUrl()) return null
    val connection = URL(url).openConnection()
    connection.connect()
    return BitmapFactory.decodeStream(connection.getInputStream())
}


