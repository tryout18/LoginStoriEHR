package com.edgarhrdevs.loginstoriehr.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(): ViewModel() {
   var uiState by mutableStateOf(LoginUiState())
       private set

    fun setEmail(text: String){
        uiState = uiState.copy(email = text)
    }

    fun setPassword(text: String){
        uiState = uiState.copy(password = text)
    }
}