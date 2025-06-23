package com.example.yourevent2.BaseDados

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class EventoRepository(private val dao: EventoDao) {
    val eventosCompletos: LiveData<List<EventoCompleto>> = dao.listarEventosCompletos()


    suspend fun inserirEvento(evento: Evento): Long = dao.inserirEvento(evento)
    suspend fun inserirCoisa(coisa: CoisaAFazer) = dao.inserirCoisa(coisa)
    suspend fun inserirParticipante(participante: Participante) = dao.inserirParticipante(participante)

    fun getNomesParticipantes(eventoId: Int): Flow<List<String>> {
        return dao.getNomesParticipantesEventoId(eventoId)
    }
    fun getNomeEventos(): Flow<List<String>> {
        return dao.getNomeEventos()
    }
    fun apagarTodosEventos() {
        dao.apagarTodosEventos()
    }
    fun apagarEventoPorId(eventoId: Int) {
        dao.apagarEventoPorId(eventoId)
    }
    fun obterEventoPorNome(nome: String): Evento? {
        return dao.obterEventoPorNome(nome)
    }
    suspend fun obterIdEventoPorNome(nome: String): Int? {
        return dao.obterIdEventoPorNome(nome)
    }
    fun obterNomesParticipantes(eventoId: Int): Flow<List<String>> {
        return dao.getNomesParticipantes(eventoId)
    }

    fun getParticipantesPorEventoId(eventoId: Int): Flow<List<Participante>> {
        return dao.getParticipantesPorEventoId(eventoId)
    }
    fun getParticipantePorId(participanteId: Int): Flow<Participante> {
        return dao.getParticipantePorId(participanteId)
    }
    fun atualizarPagamento(participanteId: Int, pago: Boolean) {
        dao.atualizarPagamento(participanteId, pago)
    }
    fun apagarParticipantePorId(participanteId: Int) {
        dao.apagarParticipantePorId(participanteId)
    }
    fun getCustoAfazerPorEventoId(eventoId: Int): Flow<List<Double>> {
        return dao.getCustoAfazerPorEventoId(eventoId)
    }
    fun atualizarCusto(eventoId: Int, novoCusto: Double) {
        dao.atualizarCusto(eventoId, novoCusto)
    }
    fun getCoisasAfazerPorEventoId(eventoId: Int): Flow<List<CoisaAFazer>> {
        return dao.getCoisasAfazerPorEventoId(eventoId)
    }
    fun getCoisaPorId(participanteId: Int): Flow<CoisaAFazer> {
        return dao.getCoisaPorId(participanteId)
    }
    fun apagarCoisaPorId(coisaId: Int) {
        dao.apagarCoisaPorId(coisaId)
    }
    fun atualizarConcluidaCoisa(coisaId: Int, concluida: Boolean) {
        dao.atualizarConcluidaCoisa(coisaId, concluida)
    }
    fun getTelefonesPorEventoId(idEvento: Int): Flow<List<String>> {
        return dao.getTelefonesPorEventoId(idEvento)
    }
    fun getEventosCompletos(): Flow<List<Evento>> {
       return dao.getEventosCompletos()
    }
    fun getCoisasAfazer(): Flow<List<CoisaAFazer>> {
        return dao.getCoisasAfazer()
    }
}

