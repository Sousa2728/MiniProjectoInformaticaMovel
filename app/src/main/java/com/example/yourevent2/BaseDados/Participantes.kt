package com.example.yourevent2.BaseDados

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "participantes",
    foreignKeys = [ForeignKey(
        entity = Evento::class,
        parentColumns = ["id"],
        childColumns = ["eventoId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Participante(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventoId: Int,
    val nome: String,
    val idade: Int,
    val telefone:String,
    val pago: Boolean

)
