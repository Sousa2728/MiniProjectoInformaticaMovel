package com.example.yourevent2.scrips

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourevent2.BaseDados.EventoRepository
import com.example.yourevent2.BaseDados.EventoViewModel
import com.example.yourevent2.BaseDados.EventoViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EnviarMensagem(
    repo: EventoRepository,
    onClick: () -> Unit,
    nomeEvento: String
) {
    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))
    var eventoId by remember { mutableStateOf<Int?>(null) }
    var enviarMensagem by remember { mutableStateOf(false) }

    LaunchedEffect(nomeEvento) {
        eventoId = viewModel.obterIdEventoPorNome(nomeEvento)
        Log.d("SMS_DEBUG", "eventoId carregado: $eventoId")
    }

    var texto by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Mensagem") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                enviarMensagem = true
            },
            enabled = texto.isNotEmpty()
        ) {
            Text("Enviar Mensagem")
        }

        if (enviarMensagem && eventoId != null) {
            EnviarMensagemSMS(viewModel, eventoId!!, texto) {
                enviarMensagem = false
                onClick() // só fecha ou avança DEPOIS de abrir a app de SMS
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
        Log.d("SMS_DEBUG", "Números recebidos: $numerosTelefone")
        Log.d("SMS_DEBUG", "Mensagem: $texto")

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
