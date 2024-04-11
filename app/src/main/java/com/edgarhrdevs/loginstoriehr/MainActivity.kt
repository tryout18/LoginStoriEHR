package com.edgarhrdevs.loginstoriehr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.edgarhrdevs.loginstoriehr.ui.navigation.NavGraph
import com.edgarhrdevs.loginstoriehr.ui.theme.LoginStoriEHRTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginStoriEHRTheme {
                val navController = rememberNavController()

                NavGraph(navController)
            }
        }
    }
}