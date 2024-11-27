package com.salem.notifications.domian.models

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class Error(
    @SerializedName("code")
    val code: Int ? = null,
    @SerializedName("details")
    val details: List<Detail> ? = null,
    @SerializedName("message")
    val message: String ? = null,
    @SerializedName("status")
    val status: String ? = null
)