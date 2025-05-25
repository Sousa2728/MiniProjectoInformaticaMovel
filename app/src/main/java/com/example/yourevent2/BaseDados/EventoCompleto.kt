package com.example.yourevent2.BaseDados

import androidx.room.Embedded
import androidx.room.Relation

data class EventoCompleto(
    @Embedded val evento: Evento,
    @Relation(parentColumn = "id", entityColumn = "eventoId")
    val coisasAFazer: List<CoisaAFazer>,
    @Relation(parentColumn = "id", entityColumn = "eventoId")
    val participantes: List<Participante>
)
