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

package com.example.chocolateapp.network

import com.example.chocolateapp.data.entity.ChocoSetEntity
import com.example.chocolateapp.data.entity.ChocolateEntity
import com.example.chocolateapp.data.entity.FormEntity
import com.example.chocolateapp.data.entity.FormInSet
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChocolateApiService {
    @GET("products?api_key=ilovemariavasileva")
    suspend fun getChocolates(): List<ChocolateEntity>

    @GET("forms?api_key=ilovemariavasileva")
    suspend fun getForms(): List<FormEntity>

    @GET("forms_in_sets?api_key=ilovemariavasileva")
    suspend fun getFormsInSets(): List<FormInSet>

    @GET("sets?api_key=ilovemariavasileva")
    suspend fun getSets(): List<ChocoSetEntity>

    @FormUrlEncoded
    @POST("order")
    suspend fun sendOrder(
        @Field("customer_name") customerName: String,
        @Field("phone") phone: String,
        @Field("total") total: Int,
        @Field("info") info: String,
        @Field("description") description: String,
        @Field("type") type: String
    ) : Response<ResponseBody>

    @Headers("Content-Type: text/plain")
    @POST("promocode")
    suspend fun checkPromocode(
        @Body promocode: RequestBody
    ): Response<ResponseBody>
}