package com.example.chocolateapp.data

import android.content.Context
import com.example.chocolateapp.network.ChocolateApiService
import com.example.chocolateapp.repository.ChocolateOfflineRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer  {
    val chocolateRepository : ChocolateRepository

}

class AppDataContainer (private val context: Context) : AppContainer {
    private val baseUrl = "https://choco38.ru/api/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : ChocolateApiService by lazy {
        retrofit.create(ChocolateApiService::class.java)
    }

    override val chocolateRepository: ChocolateRepository by lazy {
        ChocolateOfflineRepository(
            retrofitService,
            ChocolateDatabase.getDatabase(context).chocolateDao()
        )
    }
}