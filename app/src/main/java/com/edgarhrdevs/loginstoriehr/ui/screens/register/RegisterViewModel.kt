package com.edgarhrdevs.loginstoriehr.ui.screens.register

import android.content.Context
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgarhrdevs.loginstoriehr.data.Resource
import com.edgarhrdevs.loginstoriehr.data.datasources.CameraDataSource
import com.edgarhrdevs.loginstoriehr.framework.FireBaseAndroidAuthDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: CameraDataSource,
    private val authRepository: FireBaseAndroidAuthDataSource
) : ViewModel() {
    private var _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun setEmail(text: String) {
        _uiState.update { it.copy(email = text) }

    }

    fun setPassword(text: String) {
        _uiState.update { it.copy(password = text) }
    }

    fun setName(text: String) {
        _uiState.update { it.copy(name = text) }
    }

    fun setLastName(text: String) {
        _uiState.update { it.copy(lastName = text) }
    }

    fun setShowCamera(value: Boolean) {
        _uiState.update { it.copy(showCamera = value) }
    }

    fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) {
        viewModelScope.launch {
            repo.showCameraPreview(
                previewView,
                lifecycleOwner
            )
        }
    }

    fun captureAndSave(context: Context) {
        viewModelScope.launch {
            repo.captureAndSaveImage(context)
        }
    }

    fun signUp() {
        viewModelScope.launch {
            val result = authRepository.signUp(
                "${uiState.value.name} ${uiState.value.lastName}",
                uiState.value.email,
                uiState.value.password
            )
            result.fold(ifLeft = { error ->
                _uiState.update { it.copy(isLoading = true) }
                _uiState.update { it.copy(error = error) }
                _uiState.update { it.copy(showResponseAlert = false) }
                _uiState.update { it.copy(isLoading = false) }

            }) { user ->
                _uiState.update { it.copy(isLoading = true) }
                _uiState.update { it.copy(error = null) }
                _uiState.update { it.copy(showResponseAlert = true) }
                _uiState.update { it.copy(isLoading = false) }
            }

        }
    }

    fun resetScreen() {
        _uiState.update { it.copy(error = null) }
    }

    fun doneAlert() {
        viewModelScope.launch {
            _uiState.update { it.copy(showResponseAlert = false) }
            _uiState.update { it.copy(popBackStack = true) }
        }
    }
}