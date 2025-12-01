package com.example.roteiro.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.roteiro.model.Aluno
import com.example.roteiro.model.AlunoComResponsavel

@Dao
interface AlunoDao {
    @Insert
    suspend fun insert(aluno: Aluno)

    @Update
    suspend fun update(aluno: Aluno)

    @Query("SELECT * FROM alunos")
    suspend fun getAll(): List<Aluno>

    @Transaction
    @Query("SELECT * FROM alunos")
    suspend fun getAlunosComResponsaveis(): List<AlunoComResponsavel>

    @Query("SELECT * FROM alunos WHERE id = :id")
    suspend fun getById(id: Long): Aluno?

    @Transaction
    @Query("SELECT * FROM alunos WHERE id = :id")
    suspend fun getAlunoComResponsavelById(id: Long): AlunoComResponsavel?

    @Query("DELETE FROM alunos WHERE id = :id")
    suspend fun delete(id: Long)
}
