package com.example.chocolateapp.model

import kotlin.math.round
import kotlin.math.roundToInt

data class ChocolateForm (
    val chocolate: Chocolate,
    val form: Form,
    val price: Int = (chocolate.price * form.weight / 100).roundToInt()
) : Orderable
