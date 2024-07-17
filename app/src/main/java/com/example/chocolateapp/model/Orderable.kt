package com.example.chocolateapp.model

interface Orderable : Cloneable {
    val title: String
    var _price: Int
    val weight: Int
    val imageId: Int
    var amount: Int

    fun incAmount()
    fun decAmount()

    public override fun clone(): Any {
        return super.clone()
    }
}