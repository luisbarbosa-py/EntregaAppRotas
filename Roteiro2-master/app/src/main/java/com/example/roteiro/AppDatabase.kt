package com.example.roteiro

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roteiro.dao.AlunoDao
import com.example.roteiro.dao.ResponsavelDao
import com.example.roteiro.dao.UsuarioDao
import com.example.roteiro.model.Aluno
import com.example.roteiro.model.Responsavel
import com.example.roteiro.model.Usuario
import androidx.room.Room

@Database(entities = [Usuario::class, Aluno::class, Responsavel::class], version = 4) // VERSÃO INCREMENTADA
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun alunoDao(): AlunoDao
    abstract fun responsavelDao(): ResponsavelDao

    companion object {
        private const val DATABASE_NAME = "Roteiro"
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
