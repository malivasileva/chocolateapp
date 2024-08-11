package com.example.chocolateapp.network

import com.example.chocolateapp.data.entity.ChocoSetEntity
import com.example.chocolateapp.data.entity.ChocolateEntity
import com.example.chocolateapp.data.entity.FormEntity
import com.example.chocolateapp.data.entity.FormInSet
import retrofit2.http.GET

interface ChocolateApiService {
    @GET("products?api_key=ilovemariavasileva")
    suspend fun getChocolates(): List<ChocolateEntity>

    @GET("forms?api_key=ilovemariavasileva")
    suspend fun getForms(): List<FormEntity>

    @GET("forms_in_sets?api_key=ilovemariavasileva")
    suspend fun getFormsInSets(): List<FormInSet>

    @GET("sets?api_key=ilovemariavasileva")
    suspend fun getSets(): List<ChocoSetEntity>
}