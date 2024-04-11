package com.edgarhrdevs.loginstoriehr.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

) : ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun setEmail(text: String){
        uiState = uiState.copy(email = text)
    }

    fun setPassword(text: String){
        uiState = uiState.copy(password = text)
    }

    fun setName(text: String){
        uiState = uiState.copy(name = text)
    }

    fun setLastName(text: String){
        uiState = uiState.copy(lastName = text)
    }
}