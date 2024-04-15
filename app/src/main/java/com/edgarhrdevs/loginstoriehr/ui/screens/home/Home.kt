package com.edgarhrdevs.loginstoriehr.ui.screens.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.edgarhrdevs.loginstoriehr.R
import com.edgarhrdevs.loginstoriehr.domain.AppError
import com.edgarhrdevs.loginstoriehr.domain.UserMovement
import com.edgarhrdevs.loginstoriehr.ui.navigation.Destinations
import com.edgarhrdevs.loginstoriehr.ui.screens.ErrorScreen
import com.edgarhrdevs.loginstoriehr.ui.screens.LoadingScreen
import com.edgarhrdevs.loginstoriehr.ui.screens.login.GenericButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    if(uiState.navigateTo){
        navController.navigate(Destinations.LOGIN)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "¡Hola ${uiState.name}!",
                        color = Color.LightGray,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Blue),
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Función no implementada aún",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu Icon ",
                            tint = Color.LightGray
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.logOut()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "Log Out Icon",
                            tint = Color.LightGray
                        )
                    }
                }
            )
        },
        containerColor = Color.Blue
    ) { padding ->
        when (uiState.isLoading) {
            true -> {
                LoadingScreen()
            }

            false -> {
                when (uiState.error) {
                    AppError.Connectivity -> {
                        ErrorScreen(
                            text = stringResource(R.string.connectivity_error),
                            padding = padding
                        ) {
                            viewModel.resetScreen()
                        }
                    }

                    null -> {
                        Column(
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            UserDetails(context = context) {
                                viewModel.addMovement()
                            }
                            UserMovements(uiState.movements) {userMovement ->

                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "movement",
                                    value = userMovement
                                )
                                navController.navigate(Destinations.DETAILS)
                            }
                        }
                    }

                    is AppError.Unknown -> {
                        ErrorScreen(
                            text = stringResource(R.string.unknow_error),
                            padding = padding
                        ) {
                            viewModel.resetScreen()
                        }
                    }

                }
            }
        }

    }
}

@Composable
fun UserDetails(context: Context, action: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Credito Disponible", style = TextStyle(
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
        Text(
            "$3,000.00", style = TextStyle(
                color = Color.Yellow,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        )
        GenericButton(action = {
            Toast.makeText(
                context,
                "Usted a pagado su Stori :)",
                Toast.LENGTH_SHORT
            ).show()
            action()
        }, label = "Pagar mi Stori")
    }
}

@Composable
fun UserMovements(movements: List<UserMovement>?, navigation: (UserMovement) -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(20.dp)
        ) {

            items(movements ?: emptyList()) { movement ->
                UserMovementItem(movement) {
                    navigation(movement)
                }
            }
        }
    }

}

@Composable
fun UserMovementItem(movement: UserMovement, navigation: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigation()
            }, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(movement.reason)
            Text(movement.date)
        }
        Text("$${movement.amount}")
    }
}

@Preview
@Composable
fun HomePreview() {
    Home(rememberNavController())
}