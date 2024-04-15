package com.edgarhrdevs.loginstoriehr.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgarhrdevs.loginstoriehr.data.Resource
import com.edgarhrdevs.loginstoriehr.framework.FireBaseAndroidAuthDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: FireBaseAndroidAuthDataSource
) : ViewModel() {
    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun setEmail(text: String) {
        _uiState.update { it.copy(email = text) }
    }

    fun setPassword(text: String) {
        _uiState.update { it.copy(password = text) }
    }

    fun login() {
        viewModelScope.launch {

            val result = authRepository.signIn(uiState.value.email, uiState.value.password)
            result.fold(ifLeft = {error ->
                _uiState.update { it.copy(isLoading = true) }
                _uiState.update { it.copy(error = error) }
                _uiState.update { it.copy(isLoading = false) }
                _uiState.update { it.copy(navigateTo = false) }

            }){
                _uiState.update { it.copy(isLoading = true) }
                _uiState.update { it.copy(error = null) }
                _uiState.update { it.copy(isLoading = false) }
                _uiState.update { it.copy(navigateTo = true) }
            }

        }
    }

    fun resetScreen(){
        _uiState.update { it.copy(error = null) }
    }
}