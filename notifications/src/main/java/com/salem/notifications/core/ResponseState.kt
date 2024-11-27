package com.salem.notifications.core

/**
 *  Mohamed Salem
 *  5/10/2024
 *  https://www.linkedin.com/in/mhmd-salem-a004a0213/
 * https://github.com/mhmdsalem33
 */

sealed class ResponseState<T>(
    val data     : T?       = null ,
    val message  : String ? = null,
    val errors   : T?       = null,
    )
{
    class Success<T>(data : T)       : ResponseState<T>(data = data)
    class Error<T>(errors: T)    : ResponseState<T>(errors = errors)
    class Loading<T>      : ResponseState<T>()
    class Idle<T>         : ResponseState<T>()
    class EmptyData<T>    : ResponseState<T>()
    class NullData<T>     : ResponseState<T>()
    class Unauthorized<T>(message: String) : ResponseState<T>( message = message)
    class InternetConnectionError<T> : ResponseState<T>()
}
