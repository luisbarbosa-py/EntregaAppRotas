package com.example.roteiro.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TurmaComAlunos(
    @Embedded val turma: Turma,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = TurmaAlunoCrossRef::class,
            parentColumn = "turmaId",
            entityColumn = "alunoId"
        )
    )
    val alunos: List<Aluno>
)
