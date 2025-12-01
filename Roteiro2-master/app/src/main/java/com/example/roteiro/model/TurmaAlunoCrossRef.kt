package com.example.roteiro.model

import androidx.room.Entity

@Entity(primaryKeys = ["turmaId", "alunoId"], tableName = "turma_aluno_cross_ref")
data class TurmaAlunoCrossRef(
    val turmaId: Long,
    val alunoId: Long
)
