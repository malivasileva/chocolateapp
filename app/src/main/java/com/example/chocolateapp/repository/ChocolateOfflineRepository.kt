package com.example.chocolateapp.repository

import com.example.chocolateapp.data.ChocolateDao
import com.example.chocolateapp.data.ChocolateEntity
import com.example.chocolateapp.data.ChocolateRepository
import com.example.chocolateapp.network.ChocolateApiService
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ChocolateOfflineRepository (
    val api: ChocolateApiService,
    val dao : ChocolateDao
) : ChocolateRepository {
    override suspend fun insertChocolate(chocolate: ChocolateEntity) = dao.insertChocolate(chocolate)

    override suspend fun deleteChocolate(chocolate: ChocolateEntity) = dao.delteChocolate(chocolate)

    override fun getAllChocolates() : Flow<Resource<List<ChocolateEntity>>> = flow{
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

