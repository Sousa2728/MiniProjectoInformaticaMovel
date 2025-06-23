package com.example.yourevent2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun infoScreen(){

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Os teus eventos:",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            item {
                EventoItem(
                    nome = "amostra1",
                    onClick = {},
                    estado = "BOM"
                )
            }
            item {
                EventoItem(
                    nome = "amostra2",
                    onClick = {},
                    estado = "MEDIO"
                )
            }
            item {
                EventoItem(
                    nome = "amostra3",
                    onClick = {},
                    estado = "MAU"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                text = "Explicação:" +
                        "\nAmostra# = nome do evento",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Row {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp),
                tint = Color.Green
            )
            Text(
                text = "= evento sem tarefas por fazer",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Row {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp),
                tint = Color.Yellow
            )
            Text(
                text = "=evento com tarefas por fazer num prazo de mais  1 semana",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Row {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp),
                tint = Color.Red
            )
            Text(
                text =  "=evento com tarefas por fazer num prazo de 1 semana",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}
