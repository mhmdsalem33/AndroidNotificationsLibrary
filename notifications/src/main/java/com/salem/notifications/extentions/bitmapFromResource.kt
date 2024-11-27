package com.salem.notifications.extentions

/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes

fun Context.bitmapFromResource(
        @DrawableRes resId: Int
    ) = BitmapFactory.decodeResource(
        resources,
        resId
    )