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

import androidx.annotation.DrawableRes
import com.example.chocolateapp.R

data class ChocoSet(
    override val id: Int = -1,
    override val title: String,
    var forms: MutableList<ChocolateForm>,
    override var amount: Int = 1,
    @DrawableRes override val imageId: Int = R.drawable.default_chocolate,
    override val imgSrc: String,
    override var _price: Int = amount * forms.fold(0) {
            sum, form ->
        sum + form._price
    },
    override val weight: Int = amount * forms.fold(0) {
            sum, form ->
        sum + form.form.weight
    }
) : Orderable {
    fun updateChocolates(chocolates: List<Chocolate?>) {
        if (forms.size != chocolates.size) {
            throw IllegalArgumentException("Lists must have the same length")
        }

        forms.zip(chocolates).forEach { (form, chocolate) ->
            if (chocolate != null) {
                // Обновляем свойства form на основе chocolate
                form.updateChocolate(chocolate)
            }
        }
        updatePrice()
    }

    fun updateSubItem(form: ChocolateForm, chocolate: Chocolate) {
        val index = forms.indexOf(form)
        if (index != -1) {
            forms[index].updateChocolate(chocolate)
        }
        updatePrice()
    }

    private fun updatePrice() {
        _price = amount * forms.fold(0) { sum, form -> sum + form._price }
    }

    fun removeForm(form: ChocolateForm) {
        forms.remove(form)
        updatePrice()
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

    fun equalsInContent(other: ChocoSet): Boolean {
        if (this.forms.size != other.forms.size) return false
        for (i in forms.indices) {
            if (!forms[i].equalsInContent(other.forms[i])) return false
        }
        return true
    }
}
