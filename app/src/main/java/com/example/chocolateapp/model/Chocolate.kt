package com.example.chocolateapp.model

import androidx.annotation.DrawableRes
import com.example.chocolateapp.R
import kotlin.random.Random

data class Chocolate(
    val id: Int = Random.nextInt(),
    val title: String,
    val price: Int,
    val description: String = "Чудесный шоколад",
    @DrawableRes val imageId: Int = R.drawable.default_chocolate
)
