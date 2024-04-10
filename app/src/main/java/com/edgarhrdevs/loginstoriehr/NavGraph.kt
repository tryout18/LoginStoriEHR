package com.edgarhrdevs.loginstoriehr

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.edgarhrdevs.loginstoriehr.ui.navigation.Destinations
import com.edgarhrdevs.loginstoriehr.ui.screens.login.Login

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.LOGIN){
        composable(Destinations.LOGIN){
            Login()
        }
    }
}
