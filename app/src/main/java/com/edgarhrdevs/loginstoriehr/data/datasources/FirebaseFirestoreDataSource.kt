package com.edgarhrdevs.loginstoriehr.data.datasources

import arrow.core.Either
import com.edgarhrdevs.loginstoriehr.domain.AppError
import com.edgarhrdevs.loginstoriehr.domain.UserMovement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface FirebaseFirestoreDataSource {

    val userID: String?

    fun getUserMovementsFlow(): Either<AppError,Flow<List<UserMovement>>>

    suspend fun addMovement()
}