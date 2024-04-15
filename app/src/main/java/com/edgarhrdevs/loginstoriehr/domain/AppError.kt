package com.edgarhrdevs.loginstoriehr.domain


import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.io.IOException

sealed interface AppError {
    object Connectivity : AppError
    class Unknown(val message: String) :
        AppError

}

