package com.edgarhrdevs.loginstoriehr.ui.screens.register

import com.edgarhrdevs.loginstoriehr.domain.AppError

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val lastName: String = "",
    val showCamera: Boolean = false,
    val isLoading: Boolean = false,
    val error: AppError? = null,
    val popBackStack: Boolean = false,
    val showResponseAlert: Boolean = false
)
