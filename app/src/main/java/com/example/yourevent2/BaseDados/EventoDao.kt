package com.example.yourevent2.BaseDados

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {

    @Transaction
    @Query("SELECT * FROM eventos")
    fun listarEventosCompletos(): LiveData<List<EventoCompleto>>

    @Insert
    fun inserirEvento(evento: Evento): Long
    @Insert
    fun inserirCoisa(coisa:CoisaAFazer)
    @Insert
    fun inserirParticipante(participante: Participante)
    @Insert
    fun inserirCoisas(coisas: List<CoisaAFazer>)
    @Insert
    fun inserirParticipantes(participantes: List<Participante>)

    @Query("SELECT nome FROM participantes WHERE eventoId = :eventoId")
    fun getNomesParticipantesEventoId(eventoId: Int): Flow<List<String>>

    @Query("SELECT nome FROM eventos")
    fun getNomeEventos():Flow<List<String>>

    @Query("DELETE FROM eventos")
    fun apagarTodosEventos()

    @Query("DELETE FROM eventos WHERE id = :eventoId")
    fun apagarEventoPorId(eventoId: Int)

    @Query("SELECT * FROM eventos WHERE nome = :nomeEvento")
    fun obterEventoPorNome(nomeEvento: String): Evento?

    @Query("SELECT id FROM eventos WHERE nome = :nome LIMIT 1")
    suspend fun obterIdEventoPorNome(nome: String): Int?

    @Query("SELECT nome FROM participantes WHERE eventoId = :eventoId")
    fun getNomesParticipantes(eventoId: Int): Flow<List<String>>

    @Query("SELECT * FROM participantes WHERE eventoId = :eventoId")
    fun getParticipantesPorEventoId(eventoId: Int): Flow<List<Participante>>

    @Query("SELECT * FROM participantes WHERE id = :participanteId")
    fun getParticipantePorId(participanteId: Int): Flow<Participante>

    @Query("UPDATE participantes SET pago = :pago WHERE id = :participantId")
    fun atualizarPagamento(participantId: Int, pago: Boolean): Int

    @Query("DELETE FROM participantes WHERE id = :participanteId")
    fun apagarParticipantePorId(participanteId: Int)

    @Query("SELECT custo FROM coisas_afazer WHERE eventoId = :eventoId")
    fun getCustoAfazerPorEventoId(eventoId: Int): Flow<List<Double>>

    @Query("UPDATE eventos SET custo = :novoCusto WHERE id = :eventoId")
    fun atualizarCusto(eventoId: Int, novoCusto: Double)

    @Query("SELECT * FROM coisas_afazer WHERE eventoId = :eventoId")
    fun getCoisasAfazerPorEventoId(eventoId: Int): Flow<List<CoisaAFazer>>

    @Query("SELECT * FROM coisas_afazer WHERE id = :participanteId")
    fun getCoisaPorId(participanteId: Int): Flow<CoisaAFazer>

    @Query("DELETE FROM coisas_afazer WHERE id = :coisaId")
    fun apagarCoisaPorId(coisaId: Int)

    @Query("UPDATE coisas_afazer SET concluida = :concluida WHERE id = :coisaId")
    fun atualizarConcluidaCoisa(coisaId: Int, concluida: Boolean): Int

    @Query("SELECT telefone FROM participantes WHERE eventoId = :idEvento")
    fun getTelefonesPorEventoId(idEvento: Int): Flow<List<String>>

    @Query("SELECT * FROM eventos")
    fun getEventosCompletos():Flow<List<Evento>>

    @Query("SELECT * FROM coisas_afazer")
    fun getCoisasAfazer():Flow<List<CoisaAFazer>>

}

