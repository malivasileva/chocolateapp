package com.example.chocolateapp.repository

import com.example.chocolateapp.data.dao.ChocolateDao
import com.example.chocolateapp.data.entity.ChocolateEntity
import com.example.chocolateapp.data.repository.ChocolateRepository
import com.example.chocolateapp.network.ChocolateApiService
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ChocolateRepositoryImpl (
    private val api: ChocolateApiService,
    private val dao: ChocolateDao
) : ChocolateRepository {
    override suspend fun insertChocolate(chocolate: ChocolateEntity) = dao.insertChocolate(chocolate)

    override suspend fun deleteChocolate(chocolate: ChocolateEntity) = dao.delteChocolate(chocolate)

    override suspend fun getAllChocolates() : Flow<Resource<List<ChocolateEntity>>> = flow{
        emit(Resource.Loading())
        val chocolates = dao.getAllChocolates()
        emit(Resource.Loading(data = chocolates))
        try {
            val remoteChocolates = api.getChocolates()
            dao.cleareTable()
            dao.insertChocolateList(remoteChocolates)
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Problems...",
                data = chocolates
            ))
            e.printStackTrace()
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Problems...",
                data = chocolates
            ))
            e.printStackTrace()
        }

        val newChocolates = dao.getAllChocolates()
        emit(Resource.Success(
            data = newChocolates
        ))
    }

    override fun cleareChocolate() = dao.cleareTable()


}

