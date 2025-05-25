package com.example.yourevent2

import android.util.Log
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourevent2.BaseDados.Evento
import com.example.yourevent2.BaseDados.EventoRepository
import com.example.yourevent2.BaseDados.EventoViewModel
import com.example.yourevent2.BaseDados.EventoViewModelFactory
import com.example.yourevent2.BaseDados.Participante
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EnviarMensagem(repo: EventoRepository, onClick: () -> Unit, nomeEvento: String) {

    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))
    var eventoId by remember { mutableStateOf<Int?>(null) }
    var enviarMensagem by remember { mutableStateOf(false) }

    LaunchedEffect(nomeEvento) {
        eventoId = viewModel.obterIdEventoPorNome(nomeEvento)
    }

    var texto by remember { mutableStateOf("") }


    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Mensagem") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                enviarMensagem = true
                if (eventoId != null) {
                    onClick()
                }
            },
            enabled = texto.isNotEmpty()
        ) {
            Text("Enviar Mensagem")
        }
        if (enviarMensagem && eventoId != null) {
            EnviarMensagemSMS(viewModel, eventoId!!, texto) {
                enviarMensagem = false
                onClick()
            }
        }
    }
}

@Composable
fun EnviarMensagemSMS(
    viewModel: EventoViewModel,
    eventoId: Int,
    texto: String,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val numerosTelefone by viewModel.getTelefonesPorEventoId(eventoId)
        .collectAsState(initial = emptyList())

    LaunchedEffect(numerosTelefone) {
        if (numerosTelefone.isNotEmpty()) {
            val uri = Uri.parse("smsto:${numerosTelefone.joinToString(";")}")
            val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
                putExtra("sms_body", texto)
            }

            val packageManager = context.packageManager
            if (intent.resolveActivity(packageManager) != null) {
                Log.d("SMS_DEBUG", "Encontrada app para envio de SMS, a abrir...")
                context.startActivity(intent)
                onClose()
            } else {
                Log.e("SMS_DEBUG", "Nenhuma app encontrada para enviar SMS")
            }
        } else {
            Log.w("SMS_DEBUG", "Lista de números está vazia — nenhum SMS enviado.")
        }
    }
}






