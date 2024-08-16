/*
 * Copyright (C) 2024 Maria Vasileva
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
