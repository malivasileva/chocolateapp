package com.example.chocolateapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chocolateapp.data.entity.FormInSet

@Dao
interface FormInSetDao {

    @Insert
    suspend fun insert(form: FormInSet)

    @Insert
    suspend fun insertList(forms: List<FormInSet>)

    @Query("DELETE FROM form_in_set")
    fun clearTable()

    @Query("SELECT form_id FROM form_in_set WHERE set_id = :orderId")
    fun getFormsForSet(orderId: Int) : List<Int>
}