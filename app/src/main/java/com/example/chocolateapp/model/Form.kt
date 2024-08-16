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

package com.example.chocolateapp.model

import androidx.annotation.DrawableRes
import com.example.chocolateapp.R

data class Form (
    val id: Int = -1,
    val title: String,
    val weight: Int,
    @DrawableRes val imageId: Int = R.drawable.default_chocolate,
    val imgSrc: String
) {
    fun toChocolateForm() : ChocolateForm {
        return ChocolateForm(
            id = id,
            form = this,
            imgSrc = imgSrc ?: ""
        )
    }
}
