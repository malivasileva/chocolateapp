package com.example.chocolateapp.model

import android.util.Log

data class ChocolateForm (
    private var _chocolate: Chocolate? = null,
    val form: Form,
    override val imageId: Int = form.imageId,
    override val title: String = form.title,
    override var _price: Int = if (_chocolate != null) (_chocolate.price * form.weight / 100) else 0,
    override val weight: Int = form.weight,
    override var amount: Int = 1


) : Orderable {
    val chocolate: Chocolate?
        get() = _chocolate

    val price: Int
        get() = _price

    fun updateChocolate(newChocolate: Chocolate?) {
        _chocolate = newChocolate
        updatePrice()
    }

    private fun updatePrice() {
        _price = if (_chocolate != null) (_chocolate!!.price * form.weight / 100) * amount else 0
    }

    override fun incAmount() {
        Log.d("ammmmm3", amount.toString())
        amount++
        updatePrice()
        Log.d("ammmmm4", amount.toString())
    }


    override fun decAmount() {
        Log.d("ammmmm1", amount.toString())
        if (amount > 1) {
            amount--
            updatePrice()
        }
        Log.d("ammmmm2", amount.toString())
    }
}
