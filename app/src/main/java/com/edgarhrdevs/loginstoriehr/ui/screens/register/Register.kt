package com.edgarhrdevs.loginstoriehr.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.edgarhrdevs.loginstoriehr.ui.screens.login.GenericButton
import com.edgarhrdevs.loginstoriehr.ui.screens.login.GenericTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Crear Cuenta",
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Register Back Icon"
                        )
                    }

                })
        }
    ) { padding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            GenericTextField(
                textParam = uiState.email,
                label = "Ingresa tu correo electronico",
                setValue = { viewModel.setEmail(it) },
                icon = Icons.Filled.Email,
                contentDescription = "Register Email Icon",
                keyboardType = KeyboardType.Email,
                visualTransformation = VisualTransformation.None
            )
            GenericTextField(
                textParam = uiState.password,
                label = "Ingresa tu contraseña",
                setValue = { viewModel.setPassword(it) },
                icon = Icons.Filled.Lock,
                contentDescription = "Register Password Icon",
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )
            GenericTextField(
                textParam = uiState.name,
                label = "Ingresa tu nombre",
                setValue = { viewModel.setName(it) },
                icon = Icons.Filled.Person,
                contentDescription = "Register Name Icon",
                keyboardType = KeyboardType.Text,
                visualTransformation = VisualTransformation.None
            )
            GenericTextField(
                textParam = uiState.lastName,
                label = "Ingresa tus apellidos",
                setValue = { viewModel.setLastName(it) },
                icon = Icons.Filled.Person,
                contentDescription = "Register Last Name Icon",
                keyboardType = KeyboardType.Text,
                visualTransformation = VisualTransformation.None
            )
            GenericButton(action = { /*TODO*/ }, label = "Fotografia Identificación")
            GenericButton(action = { /*TODO*/ }, label = "Crear Cuenta")
        }
    }
}

@Composable
@Preview
fun RegisterPreview() {
    Register(rememberNavController())
}