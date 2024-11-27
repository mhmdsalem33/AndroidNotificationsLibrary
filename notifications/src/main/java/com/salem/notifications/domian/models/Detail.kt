package com.salem.notifications.domian.models


/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Detail(
    @SerializedName("domain")
    val domain: String ? = null,
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("reason")
    val reason: String ? = null,
    @SerializedName("@type")
    val type: String ? = null
)