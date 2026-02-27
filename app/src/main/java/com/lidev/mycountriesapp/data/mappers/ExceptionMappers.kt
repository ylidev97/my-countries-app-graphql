package com.lidev.mycountriesapp.data.mappers

import com.apollographql.apollo.exception.ApolloException
import com.lidev.mycountriesapp.domain.error.MyAppError
import java.io.IOException

internal fun Throwable.toNetworkError(): MyAppError =
    when (this) {
        is ApolloException -> {
            MyAppError.ApiError(
                code = this.cause?.hashCode() ?: -1,
                message = message ?: "Unknown error",
            )
        }

        is IOException -> {
            MyAppError.NoInternet()
        }

        else -> {
            MyAppError.Unknown()
        }
    }
