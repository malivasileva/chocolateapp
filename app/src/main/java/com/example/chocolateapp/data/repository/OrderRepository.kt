package com.example.chocolateapp.data.repository

import com.example.chocolateapp.data.entity.Order
import retrofit2.Response
import okhttp3.ResponseBody

interface OrderRepository {
    suspend fun sendOrder(order: Order) : Int
    suspend fun checkPromocode(promocode: String) : Int
}