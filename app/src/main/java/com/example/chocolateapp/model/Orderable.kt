package com.example.chocolateapp.model

interface Orderable {
    val title: String
    var _price: Int
    val weight: Int
    val imageId: Int
    var amount: Int

    fun incAmount()
    fun decAmount()

}