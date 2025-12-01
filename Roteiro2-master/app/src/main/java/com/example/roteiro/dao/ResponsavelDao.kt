package com.example.roteiro.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.roteiro.model.Responsavel

@Dao
interface ResponsavelDao {
    @Insert
    suspend fun insert(responsavel: Responsavel)

    @Update
    suspend fun update(responsavel: Responsavel)

    @Query("SELECT * FROM responsaveis")
    suspend fun getAll(): List<Responsavel>

    @Query("SELECT * FROM responsaveis WHERE id = :id")
    suspend fun getById(id: Long): Responsavel?

    @Query("DELETE FROM responsaveis WHERE id = :id")
    suspend fun delete(id: Long)
}
