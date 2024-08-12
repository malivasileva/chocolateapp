package com.example.chocolateapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Chocolate")
data class ChocolateEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Int,
    val description: String = "Чудесный шоколад",
)
