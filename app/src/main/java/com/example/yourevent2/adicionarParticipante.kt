package com.example.yourevent2

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
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
fun adicionarParticipante(repo: EventoRepository, onClick: () -> Unit, nomeEvento: String) {
    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))

    var eventoId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(nomeEvento) {
        eventoId = viewModel.obterIdEventoPorNome(nomeEvento)
    }

    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = idade,
            onValueChange = { idade = it },
            label = { Text("Idade") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = telefone,
            onValueChange = { telefone= it },
            label = { Text("Telefone") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val idadeInt = idade.toIntOrNull() ?: 0
                if (eventoId != null) {
                    val participante = Participante(0, eventoId!!, nome, idade.toIntOrNull() ?: 0, telefone, false)
                    viewModel.inserirParticipante(participante)
                    onClick()
                }
            },
            enabled = verificarDetalhes(nome,idade.toIntOrNull() ?: 0,telefone)
        ) {
            Text("Salvar Participante")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun verificarDetalhes(
    nome: String,
    idade: Int,
    telefone: String
): Boolean {
    if(nome.isEmpty() || idade < 0 || telefone.isEmpty()) return false
    if (nome.length <4|| nome.length >50) return false
    if (telefone.length !=9)return false
    if (idade > 120) return false
    if(!telefone.all { it.isDigit() }) return false
    return true
    }

