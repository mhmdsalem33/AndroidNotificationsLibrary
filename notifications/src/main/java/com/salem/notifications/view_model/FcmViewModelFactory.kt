package com.salem.notifications.view_model
/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salem.notifications.domian.use_case.SendFcmNotificationUseCase

class FcmViewModelFactory(
    private val sendFcmNotificationUseCase: SendFcmNotificationUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FcmViewModel::class.java)) {
            return FcmViewModel(sendFcmNotificationUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
