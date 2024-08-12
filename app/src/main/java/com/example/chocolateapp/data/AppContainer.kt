package com.example.chocolateapp.data

import android.content.Context
import com.example.chocolateapp.data.repository.ChocolateRepository
import com.example.chocolateapp.data.repository.ChocoSetRepository
import com.example.chocolateapp.data.repository.FormRepository
import com.example.chocolateapp.data.repository.OrderRepository
import com.example.chocolateapp.network.ChocolateApiService
import com.example.chocolateapp.repository.ChocolateRepositoryImpl
import com.example.chocolateapp.repository.ChocoSetRepositoryImpl
import com.example.chocolateapp.repository.FormRepositoryImpl
import com.example.chocolateapp.repository.OrderRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer  {
    val chocolateRepository : ChocolateRepository
    val formRepository : FormRepository
    val setRepository : ChocoSetRepository
    val orderRepository : OrderRepository
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
        ChocolateRepositoryImpl(
            retrofitService,
            ChocolateDatabase.getDatabase(context).chocolateDao()
        )
    }

    override val formRepository: FormRepository by lazy {
        FormRepositoryImpl(
            retrofitService,
            ChocolateDatabase.getDatabase(context).formDao()
        )
    }

    override val setRepository: ChocoSetRepository by lazy {
        ChocoSetRepositoryImpl(
            retrofitService,
            ChocolateDatabase.getDatabase(context).chocosetDao(),
            ChocolateDatabase.getDatabase(context).formInSetDao(),
            ChocolateDatabase.getDatabase(context).formDao(),
        )
    }

    override val orderRepository: OrderRepository by lazy {
        OrderRepositoryImpl(
            retrofitService
        )
    }
}