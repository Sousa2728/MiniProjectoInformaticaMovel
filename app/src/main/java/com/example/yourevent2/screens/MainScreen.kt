package com.example.yourevent2.screens

import com.example.yourevent2.adicionar.criarEventoScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourevent2.BaseDados.EventoRepository
import com.example.yourevent2.BaseDados.EventoViewModel
import com.example.yourevent2.BaseDados.EventoViewModelFactory
import com.example.yourevent2.detalhes.CoisaDetalhesScreen
import com.example.yourevent2.detalhes.ParticipanteDetalhesScreen
import com.example.yourevent2.detalhes.detalhesEvento
import com.example.yourevent2.detalhes.editarDetalhesEvento

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(repo: EventoRepository) {
    var currentScreen by rememberSaveable { mutableStateOf("home") }
    val viewModel: EventoViewModel = viewModel(factory = EventoViewModelFactory(repo))
    var eventoSelecionado by rememberSaveable { mutableStateOf<String?>(null) }
    var participanteSelecionado by rememberSaveable { mutableStateOf<Int?>(null) }
    var coisaSelecionada by rememberSaveable { mutableStateOf<Int?>(null) }



    Scaffold(
        bottomBar = {
            navBarr(
                homeClicado = { currentScreen = "home" },
                infoClicado = { currentScreen = "info" }
            )
        },
        floatingActionButton = {
            if (currentScreen == "home") {
                FloatingActionButton(onClick = { currentScreen = "criar_Evento" }) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar")
                }
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                "home" -> homeScreen(repo, onEventoClick = { nomeEvento ->
                    eventoSelecionado = nomeEvento
                    currentScreen = "com/example/yourevent2/detalhes"
                })

                "info" -> infoScreen()

                "com/example/yourevent2/detalhes" -> eventoSelecionado?.let { nome ->
                    detalhesEvento(
                        nomeEvento = nome,
                        onBack = {
                            currentScreen = "home"
                            eventoSelecionado = null
                        },
                        repo = repo,
                        onParticipanteClick = { idParticipante ->
                            participanteSelecionado = idParticipante
                            currentScreen = "detalhes_participante"
                        },
                        onCoisaClick = { idCoisa ->
                            coisaSelecionada = idCoisa
                            currentScreen = "detalhes_coisa"
                        },
                        onEditarClick = {
                            currentScreen = "editar_Evento"
                        }

                    )
                }

                "detalhes_participante" -> participanteSelecionado?.let { id ->
                    ParticipanteDetalhesScreen(
                        idParticipante = id,
                        onBack = {
                            currentScreen = "com/example/yourevent2/detalhes"
                            participanteSelecionado = null
                        },
                        repo = repo
                    )
                }

                "detalhes_coisa" -> coisaSelecionada?.let { id ->
                    CoisaDetalhesScreen(
                        idcoisa = id,
                        onBack = {
                            currentScreen = "com/example/yourevent2/detalhes"
                            coisaSelecionada = null
                        },
                        repo = repo
                    )
                }

                "criar_Evento" -> criarEventoScreen(repo = repo, onClick = {
                    currentScreen = "home"
                })

                "editar_Evento" -> eventoSelecionado?.let { nome ->
                    editarDetalhesEvento(
                        nomeEvento = nome,
                        onBack = {
                            currentScreen = "home"
                            eventoSelecionado = null
                        },
                        repo = repo
                    )
                }
            }
        }
    }
}
