package com.edgarhrdevs.loginstoriehr.framework

import android.widget.Toast
import arrow.core.Either
import com.edgarhrdevs.loginstoriehr.data.Resource
import com.edgarhrdevs.loginstoriehr.data.datasources.FirebaseAuthDataSource
import com.edgarhrdevs.loginstoriehr.domain.AppError
import com.edgarhrdevs.loginstoriehr.utils.await
import com.edgarhrdevs.loginstoriehr.utils.tryCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FireBaseAndroidAuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : FirebaseAuthDataSource {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Either<AppError, FirebaseUser> = tryCall {

        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        result?.user?.updateProfile(
            UserProfileChangeRequest.Builder().setDisplayName(name).build()
        )?.await()
        result.user!!

    }

    override suspend fun signIn(email: String, password: String): Either<AppError,FirebaseUser> = tryCall {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        result.user!!
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }
}