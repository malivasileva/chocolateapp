package com.example.chocolateapp.data.repository

import com.example.chocolateapp.data.entity.FormEntity
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface FormRepository {
    suspend fun insertAllForms(forms: List<FormEntity>)
    suspend fun insertForm(form: FormEntity)
    suspend fun getAllForms() : Flow<Resource<List<FormEntity>>>
    fun clearForms()
}