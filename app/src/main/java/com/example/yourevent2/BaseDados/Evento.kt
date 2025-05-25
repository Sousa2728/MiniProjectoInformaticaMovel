package com.example.yourevent2.BaseDados

import android.icu.util.LocaleData
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventos")
data class Evento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val data: String,
    val local: String,
    val custo: Double,
    val observacoes: String
)