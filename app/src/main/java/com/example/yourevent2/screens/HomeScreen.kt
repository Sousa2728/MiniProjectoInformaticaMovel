package com.example.yourevent2.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourevent2.BaseDados.CoisaAFazer
import com.example.yourevent2.BaseDados.EventoRepository
import com.example.yourevent2.BaseDados.EventoViewModel
import com.example.yourevent2.BaseDados.EventoViewModelFactory
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun homeScreen(repo: EventoRepository,onEventoClick: (String) -> Unit) {
    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))
    val eventosCompletos by viewModel.getEventosCompletos().collectAsState(initial = emptyList())
    val coisasAfazer by viewModel.getCoisasAfazer().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Os teus eventos:",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (eventosCompletos.isEmpty()) {
            Text(text = "Não há eventos criados", color = MaterialTheme.colorScheme.onSurface)
        } else {
            LazyColumn {
                itemsIndexed(eventosCompletos) { _, evento ->
                    val estado = remember(coisasAfazer) {
                        calcularEstado(evento.id, evento.data, coisasAfazer)
                    }

                    EventoItem(
                        nome = evento.nome,
                        onClick = { onEventoClick(evento.nome) },
                        estado = estado
                    )
                }
            }
        }
    }
}

    @Composable
    fun EventoItem(nome: String, onClick: () -> Unit, estado: String) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = nome, color = MaterialTheme.colorScheme.onSurface)
                //para colocar o estado do evento.
                when (estado) {
                    "BOM" -> Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                        tint = Color.Green
                    );
                    "MEDIO" -> Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                        tint = Color.Yellow
                    );
                    "MAU" -> Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                        tint = Color.Red
                    )
                }

            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calcularEstado(idEvento: Int, data: String, tarefas: List<CoisaAFazer>): String {
        val tarefasDoEvento = tarefas.filter { it.eventoId == idEvento }
        val dataEvento = LocalDate.parse(data)
        val hoje = LocalDate.now()

        return when {
            tarefasDoEvento.isEmpty() || tarefasDoEvento.all { it.concluida } -> "BOM"
            tarefasDoEvento.any { !it.concluida } && hoje.until(dataEvento).days > 7 -> "MEDIO"
            tarefasDoEvento.any { !it.concluida } -> "MAU"
            else -> "BOM"
        }
    }



