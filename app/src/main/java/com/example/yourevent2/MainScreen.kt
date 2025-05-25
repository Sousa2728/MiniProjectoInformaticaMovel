package com.example.yourevent2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
                    currentScreen = "detalhes"
                })

                "info" -> infoScreen()

                "detalhes" -> eventoSelecionado?.let { nome ->
                    DetalhesEventoScreen(
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
                        }
                    )
                }

                "detalhes_participante" -> participanteSelecionado?.let { id ->
                    ParticipanteDetalhesScreen(
                        idParticipante = id,
                        onBack = {
                            currentScreen = "detalhes"
                            participanteSelecionado = null
                        },
                        repo = repo
                    )
                }

                "detalhes_coisa" -> coisaSelecionada?.let { id ->
                    CoisaDetalhesScreen(
                         idcoisa = id,
                        onBack = {
                            currentScreen = "detalhes"
                            coisaSelecionada = null
                        },
                        repo = repo
                    )
                }

                "criar_Evento" -> criarEventoScreen(repo = repo, onClick = {
                    currentScreen = "home"
                })
            }
        }
    }
}
