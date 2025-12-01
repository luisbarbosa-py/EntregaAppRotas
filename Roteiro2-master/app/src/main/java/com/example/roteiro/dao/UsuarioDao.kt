package com.example.roteiro.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.roteiro.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE login = :login AND senha = :senha LIMIT 1")
    suspend fun getUser(login: String, senha: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE login = :login LIMIT 1")
    suspend fun getUserByLogin(login: String): Usuario?
}
