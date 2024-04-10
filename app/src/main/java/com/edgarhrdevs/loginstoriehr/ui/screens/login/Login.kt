package com.edgarhrdevs.loginstoriehr.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Login(viewModel: LoginViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        GenericTextField(
            textParam = uiState.email,
            label = "Email",
            setValue = { viewModel.setEmail(it) },
            icon = Icons.Filled.Email,
            contentDescription = "Login Email Icon ",
            keyboardType = KeyboardType.Email,
            visualTransformation = VisualTransformation.None
        )
        GenericTextField(
            textParam = uiState.password,
            label = "Contraseña",
            setValue = { viewModel.setPassword(it) },
            icon = Icons.Filled.Lock,
            contentDescription = "Login Password Icon",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        GenericButton(action = {  }, label = "Iniciar sesión")
        RegisterUserText()
    }
}

@Preview
@Composable
fun LoginPreview() {
    Login()
}

@Composable
fun GenericTextField(
    textParam: String,
    label: String,
    setValue: (String) -> Unit,
    icon: ImageVector,
    contentDescription: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation
) {
    var text by remember {
        mutableStateOf(textParam)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        value = textParam,
        label = { Text(label) },
        onValueChange = {
            text = it
            setValue(it)
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black
        ),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation
    )
}

@Composable
fun GenericButton(action: () -> Unit, label: String) {
    Button(
        onClick = { action() }, modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(label)
    }
}

@Composable
fun RegisterUserText() {
    Text(
        modifier = Modifier
            .padding(20.dp),
        text = "¿Aún no tienes cuenta? Registrate aquí",
        color = Color.Blue,
        style = TextStyle(
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    )
}