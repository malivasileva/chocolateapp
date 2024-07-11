package com.example.chocolateapp.model

data class Set(
    val title: String,
    val forms: List<ChocolateForm>,
    val price: Float = forms.fold(0f) {
        sum, form ->
        sum + form.price
    }
) : Orderable
