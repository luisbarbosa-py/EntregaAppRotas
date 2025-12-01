package com.example.roteiro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "escolas")
data class Escola(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val endereco: String,
    val numero: String,
    val periodo: String
)
