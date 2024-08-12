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