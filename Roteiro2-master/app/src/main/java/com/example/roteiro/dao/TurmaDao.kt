package com.example.roteiro.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.roteiro.model.Turma
import com.example.roteiro.model.TurmaAlunoCrossRef
import com.example.roteiro.model.TurmaComAlunos

@Dao
interface TurmaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(turma: Turma)

    @Query("SELECT * FROM turmas ORDER BY nome ASC")
    suspend fun getAll(): List<Turma>

    @Query("SELECT * FROM turmas WHERE id = :id")
    suspend fun getById(id: Long): Turma?

    @Update
    suspend fun update(turma: Turma)

    @Query("DELETE FROM turmas WHERE id = :id")
    suspend fun delete(id: Long)

    // Métodos para a relação Turma-Aluno
    @Transaction
    @Query("SELECT * FROM turmas WHERE id = :turmaId")
    suspend fun getTurmaComAlunos(turmaId: Long): TurmaComAlunos?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlunoToTurma(crossRef: TurmaAlunoCrossRef)

    @Delete
    suspend fun removeAlunoFromTurma(crossRef: TurmaAlunoCrossRef)
}