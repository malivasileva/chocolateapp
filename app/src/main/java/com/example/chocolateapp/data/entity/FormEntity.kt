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

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chocolateapp.model.Form
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "form")
data class FormEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val weight: Int,
    @SerialName(value = "img_src") val imgSrc: String
) {
    fun toForm() : Form {
        return Form(
            id = id,
            title = title,
            weight = weight,
            imgSrc = "https://choco38.ru/static/img/" + imgSrc
        )
    }
}


