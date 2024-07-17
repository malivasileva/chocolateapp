package com.example.chocolateapp.model

import androidx.annotation.DrawableRes

data class ChocoSet(
    override val title: String,
    var forms: MutableList<ChocolateForm>,
    override var amount: Int = 1,
    @DrawableRes override val imageId: Int,
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
        // Проверка, что списки одинаковой длины
        if (forms.size != chocolates.size) {
            throw IllegalArgumentException("Lists must have the same length")
        }

        // Использование метода zip для поэлементного обновления
        forms.zip(chocolates).forEach { (form, chocolate) ->
            if (chocolate != null) {
                // Обновляем свойства form на основе chocolate
                form.updateChocolate(chocolate)
            }
        }

        // Пересчёт _price и weight после обновления
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

    /*override fun equals(other: Any?): Boolean {
        *//*if (other is ChocoSet) {
            if (this.forms.size != other.forms.size) return false
            for (i in forms.indices) {
                if (forms[i].equals(other.forms[i])) return false
            }
            return true
        }*//*
        return super.equals(other)
    }*/

    fun equalsInContent(other: ChocoSet): Boolean {
        if (this.forms.size != other.forms.size) return false
        for (i in forms.indices) {
            if (!forms[i].equalsInContent(other.forms[i])) return false
        }
        return true
    }
}
