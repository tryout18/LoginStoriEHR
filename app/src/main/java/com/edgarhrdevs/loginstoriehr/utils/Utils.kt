package com.edgarhrdevs.loginstoriehr.utils

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.edgarhrdevs.loginstoriehr.domain.AppError
import com.google.firebase.auth.FirebaseUser
import java.io.IOException

fun Throwable.toError() : AppError = when(this){
    is IOException -> AppError.Connectivity
    else -> AppError.Unknown(message ?: "")
}

inline fun <T> tryCall(action:() -> T): Either<AppError, T> = try {
    action().right()
} catch (e: Exception){
    e.toError().left()
}