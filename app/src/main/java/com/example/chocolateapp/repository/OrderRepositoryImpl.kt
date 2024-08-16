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

package com.example.chocolateapp.repository

import com.example.chocolateapp.data.entity.Order
import com.example.chocolateapp.data.repository.OrderRepository
import com.example.chocolateapp.network.ChocolateApiService
import okhttp3.RequestBody.Companion.toRequestBody

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