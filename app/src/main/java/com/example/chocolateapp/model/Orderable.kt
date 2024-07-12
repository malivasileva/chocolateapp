package com.example.chocolateapp.model

import androidx.annotation.DrawableRes

interface Orderable {
    val title: String
    val price: Int
    val weight: Int
    val imageId: Int
}