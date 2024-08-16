/*
 * Copyright (C) 2024 Maria Vasileva
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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

    @Query("SELECT * FROM form WHERE id = :id")
    fun getForm(id: Int) : FormEntity

    @Query("DELETE FROM form")
    fun clearForms()
}