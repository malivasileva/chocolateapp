package com.example.chocolateapp.data

import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Form
import com.example.chocolateapp.model.Set

object Datasource {
    val tastes = listOf(
        Chocolate(title = "Молочный", price = 300f, description = "Молочный шоколад"),
        Chocolate(title = "Белый", price = 200f, description = "Белый шоколад"),
        Chocolate(title = "Горький", price = 400f, description = "Горький шоколад"),
    )

    val forms = listOf(
        Form(title = "Слиток", weight = 1000),
        Form(title = "Плитка", weight = 100),
        Form(title = "Рыбка", weight = 5),
        Form(title = "Лодка", weight = 40),
        Form(title = "Удочка", weight = 15),
        Form(title = "Поезд 1", weight = 35),
        Form(title = "Поезд 2", weight = 35),
        Form(title = "Поезд 3", weight = 35),
    )

    val sets = listOf(
        Set(title = "Рыбаку", listOf(
            ChocolateForm( chocolate = tastes[0], form = forms[2]),
            ChocolateForm( chocolate = tastes[0], form = forms[3]),
            ChocolateForm( chocolate = tastes[0], form = forms[4]),
        )),
        Set(title = "Железная дорога", listOf(
            ChocolateForm( chocolate = tastes[2], form = forms[5]),
            ChocolateForm( chocolate = tastes[2], form = forms[6]),
            ChocolateForm( chocolate = tastes[2], form = forms[7]),
        )),
    )
}