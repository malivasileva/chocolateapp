package com.example.chocolateapp.repository

import android.util.Log
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