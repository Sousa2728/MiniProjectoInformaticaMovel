package com.example.yourevent2.BaseDados

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "coisas_afazer",
    foreignKeys = [ForeignKey(
        entity = Evento::class,
        parentColumns = ["id"],
        childColumns = ["eventoId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CoisaAFazer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventoId: Int,
    val descricao: String,
    val concluida: Boolean,
    val custo: Double
    )