package com.example.chocolateapp.model

import androidx.annotation.DrawableRes

data class ChocoSet(
    override val title: String,
    val forms: List<ChocolateForm>,
    @DrawableRes override val imageId: Int,
    override val price: Int = forms.fold(0) {
        sum, form ->
        sum + form.price
    },
    override val weight: Int = forms.fold(0) {
            sum, form ->
        sum + form.form.weight
    }
) : Orderable
