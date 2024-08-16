/*
 * Copyright (C) 2024 Maria Vasileva
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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