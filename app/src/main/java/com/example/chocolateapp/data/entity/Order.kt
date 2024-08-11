package com.example.chocolateapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Order")
data class Order(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "customer_name")
    val customerName: String,
    val phone: String
)
