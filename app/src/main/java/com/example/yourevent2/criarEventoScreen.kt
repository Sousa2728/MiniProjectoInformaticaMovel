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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun criarEventoScreen(repo: EventoRepository, onClick: () -> Unit) {
    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))
    val nomeEvento by viewModel.nomeEventos().collectAsState(initial = emptyList())

    var nome by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }
    var data by remember { mutableStateOf(LocalDate.now()) }
    var local by remember { mutableStateOf("") }

    val contexto = LocalContext.current
    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val datePickerDialog = DatePickerDialog(
        contexto,
        { _: DatePicker, ano: Int, mes: Int, dia: Int ->
            data = LocalDate.of(ano, mes + 1, dia)
        },
        data.year,
        data.monthValue - 1,
        data.dayOfMonth
    )

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Evento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = local,
            onValueChange = { local = it },
            label = { Text("Local do Evento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = observacoes,
            onValueChange = { observacoes = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = data.format(formato),
            onValueChange = {},
            label = { Text("Data do Evento") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            enabled = false,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Selecionar data")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                var evento = Evento(0, nome, data.toString(), local, 0.0, observacoes)
                viewModel.inserirEvento(evento)
                onClick()
            },
            enabled = verificarDetalhes(nome, data, local, observacoes,nomeEvento),
        ) {
            Text("Salvar Evento")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun verificarDetalhes(
    nome: String,
    data: LocalDate,
    local: String,
    observacoes: String,
    nomeEvento: List<String>
): Boolean {
    val hoje = LocalDate.now()
    if (nome.isEmpty() || data.isBefore(hoje) || local.isEmpty()) {
        return false
    }
    if (nome.length > 20) return false
    if( nomeEvento.contains(nome)) return false
    if (observacoes.length > 100) return false
    return true
}

