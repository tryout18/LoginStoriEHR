package com.edgarhrdevs.loginstoriehr.ui.screens.login

import com.edgarhrdevs.loginstoriehr.domain.AppError

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: AppError? = null,
    val navigateTo: Boolean = false
)
