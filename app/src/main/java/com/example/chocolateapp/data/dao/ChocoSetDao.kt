package com.example.chocolateapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chocolateapp.data.entity.ChocoSetEntity
import com.example.chocolateapp.model.ChocoSet

@Dao
interface ChocoSetDao {
    @Insert
    suspend fun insert(chocoSet: ChocoSetEntity)

    @Insert
    suspend fun insertList(chocoSets: List<ChocoSetEntity>)

    @Query("DELETE FROM chocoset")
    fun clearTable()

    @Query("SELECT * FROM chocoset")
    fun getAllChocosets() : List<ChocoSetEntity>
}