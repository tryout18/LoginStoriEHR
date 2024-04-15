package com.edgarhrdevs.loginstoriehr.ui.navigation

import com.google.firebase.auth.FirebaseUser

data class NavigationUiState(
    val user: FirebaseUser? = null
)
