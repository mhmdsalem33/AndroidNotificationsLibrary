package com.salem.notifications.domian.models.fcm_request

data class Message(
    val token: String ? = null,
    val topic : String ? = null,
    val notification: Notification? = null,
    val data: Map<String, Any> ? = null
)