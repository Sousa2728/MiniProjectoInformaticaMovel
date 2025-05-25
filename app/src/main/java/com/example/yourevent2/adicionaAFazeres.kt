package com.example.yourevent2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourevent2.BaseDados.CoisaAFazer
import com.example.yourevent2.BaseDados.EventoRepository
import com.example.yourevent2.BaseDados.EventoViewModel
import com.example.yourevent2.BaseDados.EventoViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun adicionarAFazeres(repo: EventoRepository, onClick: () -> Unit, nomeEvento: String) {
    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))

    var eventoId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(nomeEvento) {
        eventoId = viewModel.obterIdEventoPorNome(nomeEvento)
    }

    var descricao by remember { mutableStateOf("") }
    var custo by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = custo,
            onValueChange = { custo= it },
            label = { Text("Custo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (eventoId != null) {
                    val coisaAFazer = CoisaAFazer(0, eventoId!!,descricao,false, custo.toDoubleOrNull() ?: 0.0)
                    viewModel.inserirCoisa(coisaAFazer)
                    onClick()
                }
            },
            enabled = verificarAFazeres(descricao,custo.toDoubleOrNull() ?: 0.0)
        ) {
            Text("Salvar coisa a fazer")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun verificarAFazeres(
    descricao: String,
    custo: Double
): Boolean {
    if(descricao.isEmpty() || custo<0) return false
    return true
}

