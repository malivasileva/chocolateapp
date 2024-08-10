package com.example.chocolateapp.data

import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChocolateRepository {
    suspend fun insertChocolate(chocolate : ChocolateEntity)

//    suspend fun insertChocolateList(chocolates : List<ChocolateEntity>)
    suspend fun deleteChocolate(chocolate : ChocolateEntity)
    fun getAllChocolates() : Flow<Resource<List<ChocolateEntity>>>

    fun cleareChocolate()
}