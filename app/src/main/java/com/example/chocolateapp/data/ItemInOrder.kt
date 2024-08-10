package com.example.chocolateapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Item_in_order")
data class ItemInOrder(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "order_id")
    val orderId: Int,
    @ColumnInfo(name = "form_id")
    val formId: Int,
    @ColumnInfo(name = "chocolate_id")
    val chocolateId: Int
)
