package com.example.yourevent2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun navBarr(modifier: Modifier = Modifier,
            homeClicado: () -> Unit,
            infoClicado: () -> Unit){
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = homeClicado,
                )
             {
                 Column(horizontalAlignment = Alignment.CenterHorizontally) {
                     Icon(
                         imageVector = Icons.Default.Home,
                         contentDescription = "Home",
                         tint = MaterialTheme.colorScheme.tertiary
                     )
                     Spacer(modifier = Modifier.height(4.dp))
                     Text(
                         text = ("Home"),
                         fontSize = 20.sp,
                         color = MaterialTheme.colorScheme.tertiary
                     )
                 }
            }

            Button(
                onClick =infoClicado,
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = ("Info"),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }


            }
        }
    }

    @Preview
    @Composable
    fun navBarrPrevi() {
        navBarr(Modifier.fillMaxSize(), homeClicado = {}, infoClicado = {})
    }
