package com.example.yourevent2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourevent2.BaseDados.EventoRepository
import com.example.yourevent2.BaseDados.EventoViewModel
import com.example.yourevent2.BaseDados.EventoViewModelFactory
import com.example.yourevent2.BaseDados.Participante
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.yourevent2.BaseDados.CoisaAFazer


@Composable
fun CoisaDetalhesScreen(idcoisa: Int,onBack: () -> Unit,repo: EventoRepository) {

    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))
    val coisa by viewModel.getCoisaPorId(idcoisa).collectAsState(initial = null)
    var concluida by remember { mutableStateOf(false) }
    var eliminar by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Coisa a fazer",
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (coisa != null) {
            mostrarCoisa(text = "Descriçao:${coisa!!.descricao}")
            mostrarCoisa(text = "Custo:${coisa!!.custo}")
            if (coisa!!.concluida) {
                mostrarParticipanteComIcone(text = "Concluida", icon = Icons.Default.CheckCircle)
            } else {
                mostrarParticipanteComIcone(text = "Por Fazer", icon = Icons.Default.Warning)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { concluida = true }) {
                    Text(text = "Marcar como concluida")
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { eliminar = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("Eliminar")
            }
        }
        if (concluida) {
            viewModel.atualizarConcluidaCoisa(idcoisa, true)
            concluida = false
        }
        if (eliminar) {
            viewModel.apagarCoisaPorId(idcoisa)
            onBack()
            eliminar = false
        }
    }
}

@Composable
fun mostrarCoisa(text: String) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}



