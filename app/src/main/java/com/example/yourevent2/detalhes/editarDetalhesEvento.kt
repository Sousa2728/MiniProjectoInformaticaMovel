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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourevent2.BaseDados.EventoRepository
import com.example.yourevent2.BaseDados.EventoViewModel
import com.example.yourevent2.BaseDados.EventoViewModelFactory
import com.example.yourevent2.BaseDados.Participante
import com.example.yourevent2.adicionar.adicionarAFazeres
import com.example.yourevent2.adicionar.adicionarParticipante
import com.example.yourevent2.adicionar.verificarDetalhes
import com.example.yourevent2.scrips.EnviarMensagem


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editarDetalhesEvento(
    nomeEvento: String,
    onBack: () -> Unit,
    repo: EventoRepository
) {

    var showEditarLocal by remember { mutableStateOf(false) }
    var showEditarObservacoes by remember { mutableStateOf(false) }
    var showEditarCusto by remember { mutableStateOf(false) }
    var showEditarNome by remember { mutableStateOf(false) }

    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))
    val eventoState = viewModel.eventoPorNome.collectAsState(initial = null)
    val evento = eventoState.value
    val eventoId = evento?.id


    LaunchedEffect(nomeEvento) {
        viewModel.buscarEventoPorNome(nomeEvento)
    }
    LaunchedEffect(eventoId) {
        eventoId?.let {
            viewModel.getCoisasAfazerPorEventoId(it)
            viewModel.buscarParticipantesPorEventoId(it)
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Editar o evento: $nomeEvento",
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
            Column() {
                Button(
                    onClick = { showEditarNome = true },
                    Modifier.padding(5.dp)
                ) { mostraInfo("Nome: ${evento.nome}") }

                Button(
                    onClick = { showEditarLocal = true },
                    Modifier.padding(5.dp)
                ) { mostraInfo("Local: ${evento.local}") }
                Button(
                    onClick = { showEditarCusto = true },
                    Modifier.padding(5.dp)
                ) { mostraInfo("Custo: ${evento.custo}") }
                Button(
                    onClick = { showEditarObservacoes = true },
                    Modifier.padding(5.dp)
                ) { mostraInfo("Observações: ${evento.observacoes}") }
            }
        }

        if (showEditarNome) {
            ModalBottomSheet(onDismissRequest = { showEditarNome = false }) {
                editarNome(repo, { showEditarNome = false }, nomeEvento)
            }
        }

        if (showEditarLocal) {
            ModalBottomSheet(onDismissRequest = { showEditarLocal = false }) {
                editarLocal(repo, { showEditarLocal = false }, nomeEvento)
            }
        }

        if (showEditarCusto) {
            ModalBottomSheet(onDismissRequest = { showEditarCusto = false }) {
                editarCusto(repo, { showEditarCusto = false }, nomeEvento)
            }
        }

        if (showEditarObservacoes) {
            ModalBottomSheet(onDismissRequest = { showEditarObservacoes = false }) {
                editarObservacoes(repo, { showEditarObservacoes = false }, nomeEvento)
            }


        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun editarNome(repo: EventoRepository, onClick: () -> Unit, nomeEvento: String) {
    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))

    var eventoId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(nomeEvento) {
        eventoId = viewModel.obterIdEventoPorNome(nomeEvento)
    }

    var nome by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (eventoId != null) {
                    viewModel.atualizarNome(eventoId!!, nome)
                    onClick()
                }
            }
        ) {
            Text("Editar Nome")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun editarLocal(repo: EventoRepository, onClick: () -> Unit, nomeEvento: String) {
    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))

    var eventoId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(nomeEvento) {
        eventoId = viewModel.obterIdEventoPorNome(nomeEvento)
    }

    var Local by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = Local,
            onValueChange = { Local = it },
            label = { Text("Local") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (eventoId != null) {
                    viewModel.atualizarLocal(eventoId!!, Local)
                    onClick()
                }
            }
        ) {
            Text("Editar Local")
        }
    }
}

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun editarCusto(repo: EventoRepository, onClick: () -> Unit, nomeEvento: String) {
        val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))

        var eventoId by remember { mutableStateOf<Int?>(null) }

        LaunchedEffect(nomeEvento) {
            eventoId = viewModel.obterIdEventoPorNome(nomeEvento)
        }

        var custo by remember { mutableStateOf("") }
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = custo,
                onValueChange = { custo = it },
                label = { Text("custo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(16.dp))
            val apenasNumeros = custo.all { it.isDigit() }

            Button(
                onClick = {
                    if (eventoId != null) {
                        viewModel.atualizarCusto(eventoId!!, custo.toDoubleOrNull() ?: 0.0)
                        onClick()
                    }
                },
                enabled = apenasNumeros && custo.isNotEmpty()
            ) {
                Text("Editar Custo")
            }
        }
    }

            @RequiresApi(Build.VERSION_CODES.O)
            @Composable
            fun editarObservacoes(repo: EventoRepository, onClick: () -> Unit, nomeEvento: String) {
                val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))

                var eventoId by remember { mutableStateOf<Int?>(null) }

                LaunchedEffect(nomeEvento) {
                    eventoId = viewModel.obterIdEventoPorNome(nomeEvento)
                }

                var nome by remember { mutableStateOf("") }
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Observaçoes") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (eventoId != null) {
                                viewModel.atualizarObservacoes(eventoId!!, nome)
                                onClick()
                            }
                        }
                    ) {
                        Text("Editar observaçoes")
                    }
                }
            }







