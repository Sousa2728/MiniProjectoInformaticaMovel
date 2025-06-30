package com.example.yourevent2.detalhes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourevent2.BaseDados.EventoRepository
import com.example.yourevent2.BaseDados.EventoViewModel
import com.example.yourevent2.BaseDados.EventoViewModelFactory
import com.example.yourevent2.adicionar.adicionarAFazeres
import com.example.yourevent2.adicionar.adicionarParticipante
import com.example.yourevent2.scrips.EnviarMensagem


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun detalhesEvento(
    nomeEvento: String,
    onBack: () -> Unit,
    repo: EventoRepository,
    onParticipanteClick: (Int) -> Unit,
    onCoisaClick: (Int) -> Unit,
    onEditarClick: () -> Unit
) {

    var editar by remember { mutableStateOf(false) }
    var showEnviarMensagem by remember { mutableStateOf(false) }
    val showAddParticipante = remember { mutableStateOf(false) }
    val showAddCoisas = remember { mutableStateOf(false) }
    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))
    val eventoState = viewModel.eventoPorNome.collectAsState(initial = null)
    val evento = eventoState.value
    val participantes by viewModel.participantesPorEvento.collectAsState()
    val eventoId = evento?.id
    val coisasAfazer by viewModel.coisasAfazerPorEvento.collectAsState(initial = emptyList())


    LaunchedEffect(nomeEvento) {
        viewModel.buscarEventoPorNome(nomeEvento)
    }
    LaunchedEffect(eventoId) {
        eventoId?.let {
            viewModel.getCoisasAfazerPorEventoId(it)
            viewModel.buscarParticipantesPorEventoId(it)
        }
    }


    var itemsToShow by remember { mutableStateOf(5) }
    var itemsToShow2 by remember { mutableStateOf(5) }
    val listState = rememberLazyListState()
    val participantesListState = rememberLazyListState()
    val coisasAfazerListState = rememberLazyListState()
    var atualizarPreco by remember { mutableStateOf(false) }
    val custos by viewModel
        .getCustoAfazerPorEventoId(evento?.id ?: 0)
        .collectAsState(initial = emptyList())
    var eliminar by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Detalhes do Evento: $nomeEvento",
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (evento != null) {
            mostraInfo("Nome: ${evento.nome}")
            mostraInfo("Data: ${evento.data}")
            mostraInfo("Local: ${evento.local}")
            mostraInfo("Custo: ${evento.custo}")
            mostraInfo("Observações: ${evento.observacoes}")

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Participantes:",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { showAddParticipante.value = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar Participante",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            LazyColumn(
                state = participantesListState,
                modifier = Modifier.weight(4f)
            ) {
                val mostrarParticipantes = participantes.take(itemsToShow)
                if (mostrarParticipantes.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum participante adicionado",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    itemsIndexed(mostrarParticipantes) { _, participante ->
                        mostarParticipantes(text = participante.nome, onClick = {
                            onParticipanteClick(participante.id)
                        })
                    }
                }
                if (itemsToShow < participantes.size) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    itemsToShow = participantes.size
                                }
                            ) {
                                Text(
                                    text = "Carregar mais...",
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
                if (itemsToShow == participantes.size) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    itemsToShow = 5
                                }
                            ) {
                                Text(
                                    text = "Mostrar Menos...",
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Coisas A Fazer:",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { showAddCoisas.value = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar Coisa",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            LazyColumn(
                state = coisasAfazerListState,
                modifier = Modifier.weight(4f)
                    .padding(bottom = 18.dp)
            ) {
                val mostrarCoisas = coisasAfazer.take(itemsToShow)
                if (mostrarCoisas.isEmpty()) {
                    item {
                        Text(
                            text = "Nada a fazer",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    itemsIndexed(mostrarCoisas) { _, coisasAfazer ->
                        mostarParticipantes(text = coisasAfazer.descricao, onClick = {
                            onCoisaClick(coisasAfazer.id)
                        })
                    }
                }
                if (itemsToShow2 < coisasAfazer.size) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    itemsToShow2 = coisasAfazer.size
                                }
                            ) {
                                Text(
                                    text = "Carregar mais...",
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
                if (itemsToShow2 == coisasAfazer.size) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    itemsToShow2 = 5
                                }
                            ) {
                                Text(
                                    text = "Mostrar Menos...",
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            //para adicionar participante caixa de texto
            if (showAddParticipante.value) {
                ModalBottomSheet(onDismissRequest = { showAddParticipante.value = false }) {
                    adicionarParticipante(
                        repo = repo,
                        nomeEvento = nomeEvento,
                        onClick = { showAddParticipante.value = false }
                    )
                }
            }

            //para adicionar coisas a fazer caixa de texto
            if (showAddCoisas.value) {
                ModalBottomSheet(onDismissRequest = { showAddCoisas.value = false }) {
                    adicionarAFazeres(
                        repo = repo,
                        nomeEvento = nomeEvento,
                        onClick = { showAddCoisas.value = false }
                    )


                }
            }
            Row() {
                //para eliminar evento
                Button(onClick = {
                    eliminar = true
                },modifier = Modifier.padding(0.dp, 2.dp,2.dp,2.dp)) {
                    Text(text = "Eliminar Evento")
                }

                Button(onClick ={ onEditarClick()
                },modifier = Modifier.padding(0.dp, 2.dp,2.dp,2.dp)) {
                    Text(text = "Editar Evento")
                }

            }

            Row() {
                //para editar evento
                Button( onClick ={ showEnviarMensagem=true},
                    modifier = Modifier.padding(0.dp, 2.dp,2.dp,2.dp)) {
                    Text(text = "Enviar Mensagem")
                }

            }

            if(eliminar && evento != null){
                viewModel.apagarEventoPorId(evento.id)
                onBack()
            }

            if (showEnviarMensagem) {
                ModalBottomSheet(onDismissRequest = { showEnviarMensagem = false }) {
                    EnviarMensagem(
                        repo = repo,
                        nomeEvento = nomeEvento,
                        onClick = { showEnviarMensagem = false }
                    )
                }
            }

        }
    }
}

@Composable
fun mostraInfo(text: String) {
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
fun mostarParticipantes(text: String ,onClick: () -> Unit) {

    Spacer(modifier = Modifier.height(8.dp))
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            Text(text = text, fontSize = 20.sp)
        }
    }
}




