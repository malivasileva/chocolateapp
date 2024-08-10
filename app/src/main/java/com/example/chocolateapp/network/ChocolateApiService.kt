package com.example.chocolateapp.network

import com.example.chocolateapp.data.ChocolateEntity
import retrofit2.http.GET

interface ChocolateApiService {
    @GET("products?api_key=ilovemariavasileva")
    suspend fun getChocolates(): List<ChocolateEntity>
}