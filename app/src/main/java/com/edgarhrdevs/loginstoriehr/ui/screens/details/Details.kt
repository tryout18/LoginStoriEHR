package com.edgarhrdevs.loginstoriehr.ui.screens.details

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.edgarhrdevs.loginstoriehr.R
import com.edgarhrdevs.loginstoriehr.domain.UserMovement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(navController: NavController, movement: UserMovement?) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Detalles",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Icon "
                        )
                    }
                }
            )
        },
    ) { padding ->

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(padding), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Item(title = "Razón", msg = movement?.reason ?: "")
            }
            item {
                Item(title = "Fecha", msg = movement?.date ?: "")
            }
            item {
                Item(title = "Monto", msg = movement?.amount ?: "")
            }
            item {
                Item(title = "Descripción", msg = movement?.description ?: "")
            }
        }
    }
}

@Composable
fun Item(title: String, msg: String){
    Row(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title)
        Text(msg)
    }
}

@Preview
@Composable
fun DetailsPreview(){
    Details(rememberNavController(),UserMovement("","","","","",""))
}