package com.example.yourevent2.BaseDados

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Evento::class, CoisaAFazer::class, Participante::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
    abstract fun eventoDao(): EventoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "evento_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

