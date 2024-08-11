package com.example.chocolateapp.repository

import com.example.chocolateapp.data.dao.ChocoSetDao
import com.example.chocolateapp.data.dao.FormEntityDao
import com.example.chocolateapp.data.dao.FormInSetDao
import com.example.chocolateapp.data.entity.ChocoSetEntity
import com.example.chocolateapp.data.repository.ChocosetRepository
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.network.ChocolateApiService
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ChocosetRepositoryImpl (
    private val api: ChocolateApiService,
    private val setDao: ChocoSetDao,
    private val formInSetDao: FormInSetDao,
    private val formDao: FormEntityDao
) : ChocosetRepository {
    override suspend fun insertAllChocosets(chocosets: List<ChocoSet>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertChocoset(chocoset: ChocoSet) {
        TODO("Not yet implemented")
    }

    private fun getChocosetFromDb(): List<ChocoSet> {
        return setDao.getAllChocosets().map {chocosetEntity->
            val formsId = formInSetDao.getItemsForOrder(chocosetEntity.id)
            val chocolateFormsList = formDao.getAllFormsIn(formsId).map {
                it.toForm().toChocolateForm()
            }
            chocosetEntity.toChocoSet(
                forms = chocolateFormsList
            )
        }
    }

    override fun getAllChocosets(): Flow<Resource<List<ChocoSet>>> = flow {
        emit(Resource.Loading())

        val storedChocoSets = getChocosetFromDb()

        emit(Resource.Loading(
            data = storedChocoSets
        ))

        try {
            val remoteSets = api.getSets()
            val remoteFormInSet = api.getFormsInSets()
            clearChocosets()
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

        val newChocoSets = getChocosetFromDb()

        emit(Resource.Success(
            data = newChocoSets
        ))
    }

    override fun clearChocosets() {
        formInSetDao.clearTable()
        setDao.clearTable()
    }
}