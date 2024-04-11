package com.edgarhrdevs.loginstoriehr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.edgarhrdevs.loginstoriehr.ui.navigation.Destinations
import com.edgarhrdevs.loginstoriehr.ui.screens.home.Home
import com.edgarhrdevs.loginstoriehr.ui.screens.login.Login
import com.edgarhrdevs.loginstoriehr.ui.screens.register.Register

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.LOGIN){
        composable(Destinations.LOGIN){
            Login(navController = navController)
        }

        composable(Destinations.REGISTER){
            Register(navController = navController)
        }

        composable(Destinations.HOME){
            Home()
        }
    }
}
