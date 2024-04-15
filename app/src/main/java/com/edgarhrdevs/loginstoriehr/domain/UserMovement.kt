package com.edgarhrdevs.loginstoriehr.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserMovement(
    var id: String? = null,
    var userId: String = "",
    val amount: String = "",
    val date: String = "",
    var reason: String = "",
    var description : String = ""
) : Parcelable
