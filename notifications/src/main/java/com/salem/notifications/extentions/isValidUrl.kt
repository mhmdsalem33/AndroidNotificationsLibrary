package com.salem.notifications.extentions

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import java.net.MalformedURLException
import java.net.URL

fun String.isValidUrl(): Boolean {
    return try {
        val url = URL(this)
        (url.protocol == "http" || url.protocol == "https")
    } catch (e: MalformedURLException) {
        false // Invalid URL
    }
}
