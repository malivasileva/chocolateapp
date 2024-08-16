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

import com.example.chocolateapp.data.dao.ChocoSetDao
import com.example.chocolateapp.data.dao.FormEntityDao
import com.example.chocolateapp.data.dao.FormInSetDao
import com.example.chocolateapp.data.repository.ChocoSetRepository
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.network.ChocolateApiService
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ChocoSetRepositoryImpl (
    private val api: ChocolateApiService,
    private val setDao: ChocoSetDao,
    private val formInSetDao: FormInSetDao,
    private val formDao: FormEntityDao
) : ChocoSetRepository {

    private fun getChocoSetFromDb(): List<ChocoSet> {
        val result = mutableListOf<ChocoSet>()
        setDao.getAllChocosets().forEach {set ->
            val formIds = formInSetDao.getFormsForSet(set.id)
            val forms = mutableListOf<ChocolateForm>()
            formIds.forEach {
                forms.add(formDao.getForm(it).toForm().toChocolateForm())
            }
            result.add(
                set.toChocoSet(
                    forms = forms
                )
            )

        }
        setDao.getAllChocosets().map {chocoSetEntity->
            val formsId = formInSetDao.getFormsForSet(chocoSetEntity.id)
            val chocolateFormsList = formDao.getAllFormsIn(formsId).map {
                it.toForm().toChocolateForm()
            }
            chocoSetEntity.toChocoSet(
                forms = chocolateFormsList
            )
        }

        return result
    }

    override fun getAllChocoSets(): Flow<Resource<List<ChocoSet>>> = flow {
        emit(Resource.Loading())

        val storedChocoSets = getChocoSetFromDb()

        emit(Resource.Loading(
            data = storedChocoSets
        ))

        try {
            val remoteSets = api.getSets()
            val remoteFormInSet = api.getFormsInSets()
            clearChocoSets()
            setDao.insertList(remoteSets)
            formInSetDao.insertList(remoteFormInSet)
        } catch (e: HttpException) {
            emit(Resource.Error(
                data = storedChocoSets,
                message = "I can't get new data, sorry"
            ))
            e.printStackTrace()
        } catch (e: IOException) {
            emit(Resource.Error(
                data = storedChocoSets,
                message = "I can't get new data, sorry"
            ))
            e.printStackTrace()
        }
        val newChocoSets = getChocoSetFromDb()
        emit(Resource.Success(
            data = newChocoSets
        ))
    }

    override fun clearChocoSets() {
        formInSetDao.clearTable()
        setDao.clearTable()
    }
}