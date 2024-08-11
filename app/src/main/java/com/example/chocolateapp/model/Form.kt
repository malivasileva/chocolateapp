package com.example.chocolateapp.model

import androidx.annotation.DrawableRes
import com.example.chocolateapp.R

data class Form (
    val id: Int = -1,
    val title: String,
    val weight: Int,
    @DrawableRes val imageId: Int = R.drawable.default_chocolate,
    val imgSrc: String
) {
    fun toChocolateForm() : ChocolateForm {
        return ChocolateForm(
            form = this,
            imgSrc = imgSrc ?: ""
        )
    }
}
