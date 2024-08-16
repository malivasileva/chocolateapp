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