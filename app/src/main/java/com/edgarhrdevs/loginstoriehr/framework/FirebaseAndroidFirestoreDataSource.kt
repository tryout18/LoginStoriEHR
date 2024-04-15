package com.edgarhrdevs.loginstoriehr.framework

import arrow.core.Either
import com.edgarhrdevs.loginstoriehr.data.datasources.FirebaseFirestoreDataSource
import com.edgarhrdevs.loginstoriehr.domain.AppError
import com.edgarhrdevs.loginstoriehr.domain.UserMovement
import com.edgarhrdevs.loginstoriehr.utils.tryCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.random.Random

class FirebaseAndroidFirestoreDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val randomNumber: Random
) : FirebaseFirestoreDataSource {

    override val userID: String?
        get() = firebaseAuth.currentUser?.uid


    override suspend fun addMovement() {

        val movement = UserMovement(
            userId = userID.toString(),
            amount = randomNumber.nextInt(10000).toString(),
            date = "Fecha desconocida",
            reason = "Razon desconocida",
            description = "Nadie sabe porque se realizo este movimiento, parece que usaste tu tarjeta Stori en un estado fuera de tu control, por lo tanto debes pagar todo lo indicado :c"
        )
        firebaseFirestore.collection("userMovements").add(movement).await()

    }

    override fun getUserMovementsFlow(): Either<AppError,Flow<List<UserMovement>>> = tryCall {
        callbackFlow {
            val movements = firebaseFirestore.collection("userMovements")
                .whereEqualTo("userId", userID)
                .orderBy("date")

            val subscription = movements.addSnapshotListener { snapshot, _ ->
                snapshot?.let { querySnapshot ->
                    val movementsList = mutableListOf<UserMovement>()
                    querySnapshot.documents.forEach { items ->
                        val movement = items.toObject(UserMovement::class.java)
                        movement?.reason = movement?.reason.toString()
                        movement?.let { movementsList.add(it) }
                    }
                    trySend(movementsList).isSuccess
                }
            }


            awaitClose { subscription.remove() }
        }
    }
}