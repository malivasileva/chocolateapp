package com.example.chocolateapp.model

interface Orderable : Cloneable {
    val id: Int
    val title: String
    var _price: Int
    val weight: Int
    val imageId: Int
    val imgSrc: String
    var amount: Int

    fun incAmount()
    fun decAmount()

    public override fun clone(): Any {
        return super.clone()
    }
}