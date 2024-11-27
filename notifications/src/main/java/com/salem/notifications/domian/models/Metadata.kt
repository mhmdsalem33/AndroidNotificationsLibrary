package com.salem.notifications.domian.models

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Metadata(
    @SerializedName("method")
    val method: String ? = null,
    @SerializedName("service")
    val service: String ? = null
)