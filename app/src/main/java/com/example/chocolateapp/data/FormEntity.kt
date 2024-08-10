package com.example.chocolateapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "form")
data class FormEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val weight: Int
)
