package com.example.chocolateapp.model

import android.util.Log
import androidx.annotation.DrawableRes

data class ChocoSet(
    override val title: String,
    var forms: MutableList<ChocolateForm>,
    @DrawableRes override val imageId: Int,
    override var _price: Int = forms.fold(0) {
            sum, form ->
        sum + form._price
    },
    override val weight: Int = forms.fold(0) {
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
        _price = forms.fold(0) { sum, form -> sum + form._price }
    }

    fun removeForm(form: ChocolateForm) {
        Log.d("chocoSet2", "Removing form: $form")
        Log.d("chocoSet2", forms.size.toString())
        forms.remove(form)
        updatePrice()
        Log.d("chocoSet2", forms.size.toString())

    }
}
