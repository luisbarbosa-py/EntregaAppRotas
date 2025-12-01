package com.example.roteiro.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roteiro.model.Escola

@Dao
interface EscolaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(escola: Escola)

    @Query("SELECT * FROM escolas ORDER BY nome ASC")
    suspend fun getAll(): List<Escola>

    @Query("SELECT * FROM escolas WHERE id = :id")
    suspend fun getById(id: Long): Escola?

    @Update
    suspend fun update(escola: Escola)

    @Query("DELETE FROM escolas WHERE id = :id")
    suspend fun delete(id: Long)
}
