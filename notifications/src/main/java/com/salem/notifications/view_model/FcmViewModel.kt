package com.salem.notifications.view_model
/**
 *  Mohamed Salem
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salem.notifications.domian.models.Error
import com.salem.notifications.domian.models.FcmResponse
import com.salem.notifications.domian.models.fcm_request.FcmNotificationRequest
import com.salem.notifications.domian.use_case.SendFcmNotificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FcmViewModel( private val sendFcmNotificationUseCase: SendFcmNotificationUseCase) : ViewModel() {

    private val _fcmResponse = MutableStateFlow<FcmResponse?>(null)
    val fcmResponse = _fcmResponse.asStateFlow()

    private val _fcmLoading = MutableStateFlow(false)
    val fcmLoading = _fcmLoading.asStateFlow()

    private val _error = MutableStateFlow<Error?>(null)
    val error = _error.asStateFlow()

    fun sendFcmNotification(
        accessToken: String,
        projectId: String,
        fcmNotificationRequest: FcmNotificationRequest
    ) {
        _fcmLoading.value = true
        viewModelScope.launch {
            sendFcmNotificationUseCase(accessToken, projectId, fcmNotificationRequest)
                .collect { response ->
                    _fcmLoading.value = false
                    Log.e("TestFcm", "Notification Called $response")
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _fcmResponse.emit(it)
                            Log.e("TestFcm", "Notification sent successfully")
                        }
                    } else {
                        response.body()?.error?.let {
                            _error.emit(it)
                            Log.e("TestFcm", "Failed to send notification: $it")
                        }
                    }
                    if (response.body()?.code == 401){
                        _error.emit(
                            Error(
                                code = response.code(),
                                message = response.message()
                            )
                        )
                    }
                }
        }
    }

}
