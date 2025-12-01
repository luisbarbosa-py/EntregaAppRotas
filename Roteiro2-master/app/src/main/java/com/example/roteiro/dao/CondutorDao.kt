package com.example.roteiro.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roteiro.model.Condutor

@Dao
interface CondutorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(condutor: Condutor)

    @Query("SELECT * FROM condutores ORDER BY nome ASC")
    suspend fun getAll(): List<Condutor>

    @Query("SELECT * FROM condutores WHERE id = :id")
    suspend fun getById(id: Long): Condutor?

    @Update
    suspend fun update(condutor: Condutor)

    @Query("DELETE FROM condutores WHERE id = :id")
    suspend fun delete(id: Long)
}
