package com.example.chocolateapp.model

import kotlin.math.round
import kotlin.math.roundToInt

data class ChocolateForm (
    val chocolate: Chocolate,
    val form: Form,
    override val imageId: Int = form.imageId,
    override val title: String = form.title,
    override val price: Int = (chocolate.price * form.weight / 100),
    override val weight: Int = form.weight
) : Orderable
