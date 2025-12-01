package com.example.roteiro.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roteiro.dao.AlunoDao
import com.example.roteiro.dao.CondutorDao
import com.example.roteiro.dao.EscolaDao
import com.example.roteiro.dao.ResponsavelDao
import com.example.roteiro.dao.TurmaDao
import com.example.roteiro.dao.UsuarioDao
import com.example.roteiro.model.Aluno
import com.example.roteiro.model.Condutor
import com.example.roteiro.model.Escola
import com.example.roteiro.model.Responsavel
import com.example.roteiro.model.Turma
import com.example.roteiro.model.TurmaAlunoCrossRef
import com.example.roteiro.model.Usuario

@Database(entities = [Aluno::class, Usuario::class, Responsavel::class, Turma::class, TurmaAlunoCrossRef::class, Condutor::class, Escola::class], version = 10, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun alunoDao(): AlunoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun responsavelDao(): ResponsavelDao
    abstract fun turmaDao(): TurmaDao
    abstract fun condutorDao(): CondutorDao
    abstract fun escolaDao(): EscolaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "roteiro_database"
                ) 
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
