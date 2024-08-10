package com.example.chocolateapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Shocoset")
data class ChocoSetEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)
