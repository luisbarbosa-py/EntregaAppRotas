package com.example.roteiro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "responsaveis")
data class Responsavel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val cep: String,
    val endereco: String,
    val numero: String,
    val complemento: String?,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val telefone: String
)
