package com.example.chocolateapp.data.repository

import com.example.chocolateapp.data.entity.ChocoSetEntity
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface ChocosetRepository {
    suspend fun insertAllChocosets(chocosets: List<ChocoSet>)
    suspend fun insertChocoset(form: ChocoSet)
    fun getAllChocosets() : Flow<Resource<List<ChocoSet>>>
    fun clearChocosets()
}