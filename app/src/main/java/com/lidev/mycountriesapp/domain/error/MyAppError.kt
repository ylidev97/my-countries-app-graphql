package com.lidev.mycountriesapp.domain.error

sealed class MyAppError : Throwable() {

    data class Unknown(
        override val cause: Throwable? = null
    ) : MyAppError(){
        override val message: String = cause?.message ?: "Unknown error"
    }

    data class NoInternet(
        override val message: String = "No internet connection"
    ) : MyAppError()


    data class ApiError(
        val code: Int, override val message: String
    ) : MyAppError()

}
