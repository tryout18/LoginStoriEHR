package com.edgarhrdevs.loginstoriehr.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgarhrdevs.loginstoriehr.framework.FireBaseAndroidAuthDataSource
import com.edgarhrdevs.loginstoriehr.ui.screens.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val authRepository: FireBaseAndroidAuthDataSource
): ViewModel() {

    var uiState by mutableStateOf(NavigationUiState())
        private set

    init {
        getUser()
    }

    fun getUser(){
        viewModelScope.launch {
            uiState = uiState.copy(user = authRepository.currentUser)
        }
    }

}