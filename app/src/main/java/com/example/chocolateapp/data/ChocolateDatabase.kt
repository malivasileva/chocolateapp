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
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chocolateapp.data.dao.ChocoSetDao
import com.example.chocolateapp.data.dao.ChocolateDao
import com.example.chocolateapp.data.dao.FormEntityDao
import com.example.chocolateapp.data.dao.FormInSetDao
import com.example.chocolateapp.data.dao.ItemInOrderDao
import com.example.chocolateapp.data.dao.OrderDao
import com.example.chocolateapp.data.entity.ChocoSetEntity
import com.example.chocolateapp.data.entity.ChocolateEntity
import com.example.chocolateapp.data.entity.FormEntity
import com.example.chocolateapp.data.entity.FormInSet
import com.example.chocolateapp.data.entity.ItemInOrder
import com.example.chocolateapp.data.entity.Order

@Database(entities = [ChocolateEntity::class,
    FormEntity::class, ChocoSetEntity::class,
    FormInSet::class, Order::class, ItemInOrder::class],
    version = 8,
    exportSchema = false)
abstract class ChocolateDatabase : RoomDatabase() {
    abstract fun chocolateDao() : ChocolateDao
    abstract fun formDao() : FormEntityDao
    abstract fun orderDao() : OrderDao
    abstract fun itemInOrderDao() : ItemInOrderDao
    abstract fun chocosetDao() : ChocoSetDao
    abstract fun formInSetDao() : FormInSetDao

    companion object {
        @Volatile
        private var Instance: ChocolateDatabase? = null

        fun getDatabase(context: Context) : ChocolateDatabase {
            return Instance ?: synchronized (this) {
                Room.databaseBuilder(context, ChocolateDatabase::class.java, "chocolate_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{
                        Instance = it
                    }
            }
        }
    }


}