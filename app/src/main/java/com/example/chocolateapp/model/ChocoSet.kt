package com.example.chocolateapp.model

import androidx.annotation.DrawableRes

data class ChocoSet(
    override val title: String,
    val forms: MutableList<ChocolateForm>,
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
    fun updateChocolates(chocolates: Map<ChocolateForm, Chocolate?>) {
        forms.forEach() {
            it.updateChocolate(chocolates[it])
        }
    }
}
