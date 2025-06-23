package com.example.yourevent2.detalhes

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun ParticipanteDetalhesScreen(idParticipante: Int,onBack: () -> Unit,repo: EventoRepository) {

    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))
    val participante by viewModel.getParticipantePorId(idParticipante).collectAsState(initial = null)
    var pago by remember { mutableStateOf(false) }
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
                text = "Participante",
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (participante != null) {
            mostrarParticipante(text = "Nome:${participante?.nome}")
            mostrarParticipante(text = "Idade:${participante?.idade}")
            mostrarParticipante(text = "Telefone:${participante?.telefone}")
            if (participante?.pago == true) {
                mostrarParticipanteComIcone(text = "Pago", icon = Icons.Default.CheckCircle)
            } else {
                mostrarParticipanteComIcone(text = "Não Pago", icon = Icons.Default.Warning)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { pago = true }) {
                    Text(text = "Marcar como Pago")
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
    if (pago) {
        viewModel.atualizarPagamento(idParticipante, true)
        pago = false
    }
    if (eliminar) {
        viewModel.apagarParticipantePorId(idParticipante)
        onBack()
        eliminar = false
    }
        }
}



@Composable
fun mostrarParticipante(text: String) {
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
@Composable
fun mostrarParticipanteComIcone(text: String, icon: ImageVector) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
        Text(text = text, fontSize = 20.sp)
    }
}



