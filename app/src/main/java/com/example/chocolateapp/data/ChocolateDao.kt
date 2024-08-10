package com.example.chocolateapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChocolateDao {
    @Insert
    suspend fun insertChocolate(chocolate : ChocolateEntity)

    @Insert
    suspend fun insertChocolateList(chocolates: List<ChocolateEntity>)

    @Delete
    suspend fun delteChocolate(chocolate: ChocolateEntity)

    @Query("SELECT * from chocolate")
    fun getAllChocolates() : List<ChocolateEntity>

    @Query("DELETE FROM chocolate")
    fun cleareTable()
}