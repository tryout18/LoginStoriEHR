package com.edgarhrdevs.loginstoriehr.ui.screens.home

import com.edgarhrdevs.loginstoriehr.domain.AppError
import com.edgarhrdevs.loginstoriehr.domain.UserMovement
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val name: String = "",
    val movements: List<UserMovement> = emptyList(),
    val error: AppError? = null,
    val isLoading: Boolean = false,
    val navigateTo: Boolean = false
)
