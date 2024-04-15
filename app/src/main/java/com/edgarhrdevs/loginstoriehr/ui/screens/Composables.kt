package com.edgarhrdevs.loginstoriehr.ui.screens

import android.view.RoundedCorner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edgarhrdevs.loginstoriehr.ui.screens.login.GenericButton


@Composable
fun ErrorScreen(text: String, padding: PaddingValues, action: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text)
        GenericButton(action = { action() }, label = "Aceptar")

    }

}

@Composable
fun LoadingScreen() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponseAlert(message: String, title: String, action: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = { onDismiss() }) {
        Card(shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    title, style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    message, style = TextStyle(
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Button(onClick = {
                    action()
                }) {
                    Text("Aceptar")
                }
            }
        }

    }
}

@Composable
@Preview
fun ResponsealertPreview() {
    ResponseAlert("mensaje", "titulo", {}, {})
}