package com.edgarhrdevs.loginstoriehr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.edgarhrdevs.loginstoriehr.domain.UserMovement
import com.edgarhrdevs.loginstoriehr.ui.screens.details.Details
import com.edgarhrdevs.loginstoriehr.ui.screens.home.Home
import com.edgarhrdevs.loginstoriehr.ui.screens.login.Login
import com.edgarhrdevs.loginstoriehr.ui.screens.register.Register

@Composable
fun NavGraph(navController: NavHostController, viewModel: NavigationViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState

    LaunchedEffect(key1 = Unit) {
        viewModel.getUser()
    }

    NavHost(
        navController = navController,
        startDestination = if(uiState.user == null) Destinations.LOGIN else Destinations.HOME
    ){
        composable(Destinations.LOGIN){
            Login(navController = navController)
        }

        composable(Destinations.REGISTER){
            Register(navController = navController)
        }

        composable(Destinations.HOME){
            Home(navController = navController)
        }

        composable(Destinations.DETAILS){

            val result = navController.previousBackStackEntry?.savedStateHandle?.get<UserMovement>("movement")

            Details(navController = navController, result)
        }
    }
}
