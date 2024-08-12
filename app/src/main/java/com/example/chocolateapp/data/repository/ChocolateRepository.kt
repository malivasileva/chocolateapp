package com.example.chocolateapp.data.repository

import com.example.chocolateapp.data.entity.ChocolateEntity
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChocolateRepository {
    suspend fun insertChocolate(chocolate : ChocolateEntity)
    suspend fun deleteChocolate(chocolate : ChocolateEntity)
    suspend fun getAllChocolates() : Flow<Resource<List<ChocolateEntity>>>

    fun cleareChocolate()
}