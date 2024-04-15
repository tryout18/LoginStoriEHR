package com.edgarhrdevs.loginstoriehr.data.datasources

import arrow.core.Either
import com.edgarhrdevs.loginstoriehr.data.Resource
import com.edgarhrdevs.loginstoriehr.domain.AppError
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthDataSource {

    val currentUser: FirebaseUser?

    suspend fun signUp(name: String, email: String, password: String): Either<AppError,FirebaseUser>?

    suspend fun signIn(email: String, password: String): Either<AppError,FirebaseUser>

    fun logOut()
}