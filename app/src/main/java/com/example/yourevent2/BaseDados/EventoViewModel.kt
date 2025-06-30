package com.example.yourevent2.BaseDados

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.withContext

class EventoViewModel(private val repository: EventoRepository) : ViewModel() {

    val eventosCompletos: LiveData<List<EventoCompleto>> = repository.eventosCompletos
    private val _eventoPorNome = MutableStateFlow<Evento?>(null)
    val eventoPorNome: Flow<Evento?> get() = _eventoPorNome
    private val _participantesPorEvento = MutableStateFlow<List<Participante>>(emptyList())
    val participantesPorEvento: StateFlow<List<Participante>> get() = _participantesPorEvento
    private val _coisasAfazerPorEvento = MutableStateFlow<List<CoisaAFazer>>(emptyList())
    val coisasAfazerPorEvento: StateFlow<List<CoisaAFazer>> = _coisasAfazerPorEvento

    fun inserirEvento(evento: Evento) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.inserirEvento(evento)
        }
    }

    fun inserirCoisa(coisa: CoisaAFazer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.inserirCoisa(coisa)
        }
    }

    fun inserirParticipante(participante: Participante) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.inserirParticipante(participante)
        }
    }

    fun nomesParticipantes(eventoId: Int): Flow<List<String>> {
        return repository.getNomesParticipantes(eventoId)
    }

    fun nomeEventos(): Flow<List<String>> {
        return repository.getNomeEventos()
    }

    fun apagarTodosEventos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.apagarTodosEventos()
        }
    }

    fun apagarEventoPorId(eventoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.apagarEventoPorId(eventoId)
        }
    }

    fun buscarEventoPorNome(nome: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _eventoPorNome.value = repository.obterEventoPorNome(nome)
        }
    }

    suspend fun obterIdEventoPorNome(nome: String): Int? {
        return withContext(Dispatchers.IO) {
            repository.obterIdEventoPorNome(nome)
        }
    }

    fun obterNomesParticipantes(eventoId: Int): Flow<List<String>> {
        return repository.obterNomesParticipantes(eventoId)
    }

    fun obeterNomesParticipantesEventoNome(nomeEvento: String): Flow<List<String>> = flow {
        val id = withContext(Dispatchers.IO) {
            repository.obterIdEventoPorNome(nomeEvento)
        }
        if (id != null) {
            emitAll(repository.obterNomesParticipantes(id))
        } else {
            emit(emptyList())
        }
    }

    fun buscarParticipantesPorEventoId(eventoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getParticipantesPorEventoId(eventoId).collect { lista ->
                _participantesPorEvento.value = lista
            }
        }
    }

    fun getParticipantePorId(id: Int): Flow<Participante> {
        return repository.getParticipantePorId(id)
    }

    fun atualizarPagamento(participanteId: Int, pago: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.atualizarPagamento(participanteId, pago)
        }
    }

    fun apagarParticipantePorId(participanteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.apagarParticipantePorId(participanteId)
        }
    }

    fun getCustoAfazerPorEventoId(eventoId: Int): Flow<List<Double>> {
        return repository.getCustoAfazerPorEventoId(eventoId)
    }

    fun atualizarCusto(eventoId: Int, novoCusto: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.atualizarCusto(eventoId, novoCusto)
        }
    }

    fun getCoisasAfazerPorEventoId(eventoId: Int) {
        viewModelScope.launch {
            repository.getCoisasAfazerPorEventoId(eventoId)
                .collect { lista ->
                    _coisasAfazerPorEvento.value = lista
                }
        }
    }

    fun getCoisaPorId(participanteId: Int): Flow<CoisaAFazer> {
        return repository.getCoisaPorId(participanteId)
    }

    fun apagarCoisaPorId(coisaId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.apagarCoisaPorId(coisaId)
        }
    }

    fun atualizarConcluidaCoisa(coisaId: Int, concluida: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.atualizarConcluidaCoisa(coisaId, concluida)
        }
    }

    fun getTelefonesPorEventoId(idEvento: Int): Flow<List<String>> {
        return repository.getTelefonesPorEventoId(idEvento)
    }

    fun getEventosCompletos(): Flow<List<Evento>> {
        return repository.getEventosCompletos()
    }
    fun getCoisasAfazer(): Flow<List<CoisaAFazer>> {
        return repository.getCoisasAfazer()
    }

    fun atualizarNome(eventoId: Int, novoNome: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.atualizarNome(eventoId, novoNome)
        }
    }
    fun atualizarLocal(eventoId: Int, novoLocal: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.atualizarLocal(eventoId, novoLocal)
        }
    }
    fun atualizarObservacoes(eventoId: Int, novasObservacoes: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.atualizarObservacoes(eventoId, novasObservacoes)
        }
    }

}