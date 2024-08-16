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

package com.example.chocolateapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Form_in_set")
data class FormInSet(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "set_id")
    @SerialName(value = "set_id")
    val setId: Int,
    @ColumnInfo(name = "form_id")
    @SerialName(value = "form_id")
    val formId: Int,
)
