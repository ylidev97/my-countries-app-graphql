package com.lidev.mycountriesapp.data.util

import com.lidev.mycountriesapp.data.mappers.toNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal suspend inline fun <T> safeApiCall(
    crossinline block: suspend () -> T
): Result<T> =
    withContext(Dispatchers.IO) {
        runCatching { block() }
            .recoverCatching { exception ->
                throw exception.toNetworkError()
            }
    }

