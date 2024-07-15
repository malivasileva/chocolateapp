package com.example.chocolateapp.model

data class ChocolateForm (
    private var _chocolate: Chocolate? = null,
    val form: Form,
    override val imageId: Int = form.imageId,
    override val title: String = form.title,
    override var _price: Int = if (_chocolate != null) (_chocolate.price * form.weight / 100) else 0,
    override val weight: Int = form.weight

) : Orderable {
    val chocolate: Chocolate?
        get() = _chocolate

    val price: Int
        get() = _price

    fun updateChocolate(newChocolate: Chocolate?) {
        _chocolate = newChocolate
        _price = if (_chocolate != null) (_chocolate!!.price * form.weight / 100) else 0
    }
}
