package com.edgarhrdevs.loginstoriehr.ui.screens.register

import android.Manifest
import android.graphics.Paint.Align
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.edgarhrdevs.loginstoriehr.R
import com.edgarhrdevs.loginstoriehr.domain.AppError
import com.edgarhrdevs.loginstoriehr.ui.screens.ErrorScreen
import com.edgarhrdevs.loginstoriehr.ui.screens.LoadingScreen
import com.edgarhrdevs.loginstoriehr.ui.screens.ResponseAlert
import com.edgarhrdevs.loginstoriehr.ui.screens.login.GenericButton
import com.edgarhrdevs.loginstoriehr.ui.screens.login.GenericTextField
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun Register(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val permissions = if (Build.VERSION.SDK_INT <= 28) {
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    } else listOf(Manifest.permission.CAMERA)

    val permissionState = rememberMultiplePermissionsState(
        permissions = permissions
    )

    if (!permissionState.allPermissionsGranted) {
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
    }



    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val screeHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var previewView: PreviewView


    val uiState by viewModel.uiState.collectAsState()

    if (uiState.popBackStack) {
        navController.popBackStack()
    }

    if(uiState.showResponseAlert){
        ResponseAlert(message = "Cuenta creada exitosamente", title = "¡Éxito!", action = { viewModel.doneAlert() }) {
            viewModel.doneAlert()
        }
    }

    BackHandler {
        if (uiState.showCamera) viewModel.setShowCamera(false) else navController.popBackStack()
    }

    when (uiState.isLoading) {
        true -> {
            LoadingScreen()
        }

        false -> {
            when (uiState.error) {
                AppError.Connectivity -> {
                    ErrorScreen(
                        text = stringResource(R.string.connectivity_error),
                        padding = PaddingValues(20.dp)
                    ) {
                        viewModel.resetScreen()
                    }
                }

                is AppError.Unknown -> {
                    ErrorScreen(
                        text = (uiState.error as AppError.Unknown).message,
                        padding = PaddingValues(20.dp)
                    ) {
                        viewModel.resetScreen()
                    }
                }

                null -> {
                    when (uiState.showCamera) {
                        false -> {
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
                                    GenericButton(action = {
                                        viewModel.setShowCamera(true)
                                    }, label = "Fotografia Identificación")
                                    GenericButton(
                                        action = { viewModel.signUp() },
                                        label = "Crear Cuenta"
                                    )
                                }
                            }
                        }

                        else -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                AndroidView(
                                    factory = {
                                        previewView = PreviewView(it)
                                        viewModel.showCameraPreview(previewView, lifecycleOwner)
                                        previewView
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                                IconButton(
                                    onClick = { viewModel.captureAndSave(context) },
                                    modifier = Modifier.align(
                                        Alignment.BottomCenter
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_camera),
                                        contentDescription = "Camera Icon",
                                        tint = Color.White
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        viewModel.setShowCamera(false)
                                    },
                                    modifier = Modifier.align(Alignment.TopStart)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Close Icon",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }


}

@Composable
@Preview
fun RegisterPreview() {
    Register(rememberNavController())
}