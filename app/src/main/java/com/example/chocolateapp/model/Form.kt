package com.example.chocolateapp.model

import androidx.annotation.DrawableRes
import com.example.chocolateapp.R

data class Form(
    val title: String,
    val weight: Int,
    @DrawableRes val imageId: Int = R.drawable.default_chocolate
)
