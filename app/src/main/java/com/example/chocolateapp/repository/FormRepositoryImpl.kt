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