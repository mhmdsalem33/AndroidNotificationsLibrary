package com.salem.notifications.domian.models

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FcmResponse(
    @SerializedName("name")
    val name : String ? = null,
    @SerializedName("error")
    val error: Error? = null,
    @SerializedName("code")
    val code : Int ? = null
)
