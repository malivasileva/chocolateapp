package com.example.chocolateapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chocolateapp.data.entity.FormEntity

@Dao
interface FormEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(form: FormEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(forms: List<FormEntity>)

    @Query("SELECT * FROM form")
    fun getAllForms() : List<FormEntity>

    @Query("SELECT * FROM form WHERE id in (:idList)")
    fun getAllFormsIn(idList: List<Int>) : List<FormEntity>

    @Query("DELETE FROM form")
    fun clearForms()
}