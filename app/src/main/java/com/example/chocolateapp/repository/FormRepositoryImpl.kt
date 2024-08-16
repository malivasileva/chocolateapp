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

package com.example.chocolateapp.repository

import com.example.chocolateapp.data.dao.FormEntityDao
import com.example.chocolateapp.data.entity.FormEntity
import com.example.chocolateapp.data.repository.FormRepository
import com.example.chocolateapp.network.ChocolateApiService
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class FormRepositoryImpl (
    private val api : ChocolateApiService,
    private val dao : FormEntityDao
) : FormRepository {
    override suspend fun insertAllForms(forms: List<FormEntity>) {
        dao.insertList(forms)
    }

    override suspend fun insertForm(form: FormEntity) {
        dao.insert(form)
    }

    override suspend fun getAllForms(): Flow<Resource<List<FormEntity>>> = flow {
        emit(Resource.Loading())
        val storedForms = dao.getAllForms()
        emit(Resource.Loading(data = storedForms))
        try {
            val remoteList = api.getForms()
            dao.clearForms()
            dao.insertList(remoteList)
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Problems with forms",
                data = storedForms
            ))
            e.printStackTrace()
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Problems with forms",
                data = storedForms
            ))
            e.printStackTrace()
        }
        val resultList = dao.getAllForms()
        emit(Resource.Success(
            data = resultList
        ))
    }

    override fun clearForms() {
        dao.clearForms()
    }
}