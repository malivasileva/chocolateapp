package com.example.chocolateapp.model

data class ChocolateForm (
    override val id: Int,
    private var _chocolate: Chocolate? = null,
    val form: Form,
    val isChocoset: Boolean = false,
    override val imageId: Int = form.imageId,
    override val imgSrc: String,
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
        amount++
        updatePrice()
    }


    override fun decAmount() {
        if (amount > 1) {
            amount--
            updatePrice()
        }
    }

    fun equalsInContent(other: ChocolateForm) : Boolean {
        if (this.form == (other as? ChocolateForm)?.form
            && this.chocolate == (other as? ChocolateForm)?.chocolate) {
            return true
        }
        else return false
    }

    fun toJsonOrderItem() : JsonOrderItem {
        return JsonOrderItem(
            formId = id,
            chocolateId = chocolate?.id!!,
            chocoset = isChocoset,
            count = amount
        )
    }
}
