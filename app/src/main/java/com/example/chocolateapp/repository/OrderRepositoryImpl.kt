package com.example.chocolateapp.repository

import android.util.Log
import com.example.chocolateapp.data.entity.Order
import com.example.chocolateapp.data.repository.OrderRepository
import com.example.chocolateapp.network.ChocolateApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import okhttp3.ResponseBody

class OrderRepositoryImpl (
    private val api: ChocolateApiService
) : OrderRepository {
    override suspend fun sendOrder(order: Order) : Int  {
        return api.sendOrder(
            customerName = order.customerName,
            phone = order.phone,
            total = order.total,
            info = order.info,
            description = order.description,
            type = order.type
        ).code()
    }

    override suspend fun checkPromocode(promocode: String): Int {
        return try {
            api.checkPromocode(
                promocode.uppercase().trim().toRequestBody()
            ).body()?.string()?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}