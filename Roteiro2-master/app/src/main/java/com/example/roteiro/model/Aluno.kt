package com.example.roteiro.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "alunos",
    foreignKeys = [ForeignKey(
        entity = Responsavel::class,
        parentColumns = ["id"],
        childColumns = ["responsavelId"],
        onDelete = ForeignKey.SET_NULL
    )])
data class Aluno(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val turno: String,
    val escola: String,
    val responsavelId: Long?
)
