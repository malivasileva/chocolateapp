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

package com.example.chocolateapp.data.repository

import com.example.chocolateapp.data.entity.FormEntity
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface FormRepository {
    suspend fun insertAllForms(forms: List<FormEntity>)
    suspend fun insertForm(form: FormEntity)
    suspend fun getAllForms() : Flow<Resource<List<FormEntity>>>
    fun clearForms()
}