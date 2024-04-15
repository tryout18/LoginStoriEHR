package com.edgarhrdevs.loginstoriehr.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgarhrdevs.loginstoriehr.framework.FireBaseAndroidAuthDataSource
import com.edgarhrdevs.loginstoriehr.framework.FirebaseAndroidFirestoreDataSource
import com.edgarhrdevs.loginstoriehr.ui.screens.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: FireBaseAndroidAuthDataSource,
    private val fireStoreRepository: FirebaseAndroidFirestoreDataSource
): ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init{
        getUser()
        getMovements()
    }

    fun getUser(){
        _uiState.update { it.copy(name = authRepository.currentUser?.displayName ?: "Datos no encontrados") }
    }

    fun addMovement(){
        viewModelScope.launch {
            fireStoreRepository.addMovement()
        }
    }

    fun getMovements(){
        viewModelScope.launch {
            fireStoreRepository.getUserMovementsFlow().fold(
                ifLeft = { error ->
                    _uiState.update { it.copy(isLoading = true) }
                    _uiState.update { it.copy(error = error) }
                    _uiState.update { it.copy(isLoading = false) }
                }
            ){ flow ->
                flow.collect{ list ->
                    _uiState.update { it.copy(isLoading = true) }
                    _uiState.update { it.copy(movements = list) }
                    _uiState.update { it.copy(error = null) }
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun logOut(){
        viewModelScope.launch {
            authRepository.logOut()
            _uiState.update { it.copy(navigateTo = true) }
        }
    }

    fun resetScreen(){
        _uiState.update { it.copy(error = null) }
    }
}