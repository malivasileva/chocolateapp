package com.example.chocolateapp.data.repository

import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface ChocoSetRepository {
    fun getAllChocoSets() : Flow<Resource<List<ChocoSet>>>
    fun clearChocoSets()
}