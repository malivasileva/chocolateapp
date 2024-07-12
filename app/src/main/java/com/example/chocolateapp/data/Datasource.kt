package com.example.chocolateapp.data

import com.example.chocolateapp.R
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Form
import com.example.chocolateapp.model.ChocoSet

object Datasource {
    val tastes = listOf(
        Chocolate(title = "Молочный", price = 300, description = "Молочный шоколад", imageId = R.drawable.milk_chocolate),
        Chocolate(title = "Белый", price = 200, description = "Белый шоколад", imageId = R.drawable.white_chocolate),
        Chocolate(title = "Горький", price = 400, description = "Горький шоколад", imageId = R.drawable.dark_chocolate),
    )

    val forms = listOf(
        Form(title = "Слиток", weight = 1000, imageId = R.drawable.ingot),
        Form(title = "Плитка", weight = 100, imageId = R.drawable.bar),
        Form(title = "Рыба 1", weight = 20, imageId = R.drawable.fish),
        Form(title = "Лодка", weight = 40, imageId = R.drawable.boat),
        Form(title = "Рыба 2", weight = 15, imageId = R.drawable.fish2),
        Form(title = "Поезд 1", weight = 35, imageId = R.drawable.train1),
        Form(title = "Поезд 2", weight = 35, imageId = R.drawable.train2),
        Form(title = "Поезд 3", weight = 35, imageId = R.drawable.train3),
    )

    val chocoSets = listOf(
        ChocoSet(
            title = "Рыбаку 1",
            listOf(
            ChocolateForm( chocolate = tastes[0], form = forms[2]),
            ChocolateForm( chocolate = tastes[0], form = forms[3]),
            ChocolateForm( chocolate = tastes[0], form = forms[4]),),
            imageId = R.drawable.set_fishman1
        ),
        ChocoSet(
            title = "Рыбаку 2",
            listOf(
                ChocolateForm( chocolate = tastes[1], form = forms[2]),
                ChocolateForm( chocolate = tastes[1], form = forms[3]),
                ChocolateForm( chocolate = tastes[1], form = forms[4]),),
            imageId = R.drawable.set_fishman2
        ),
        ChocoSet(
            title = "Рыбаку 3",
            listOf(
                ChocolateForm( chocolate = tastes[2], form = forms[2]),
                ChocolateForm( chocolate = tastes[2], form = forms[3]),
                ChocolateForm( chocolate = tastes[2], form = forms[4]),),
            imageId = R.drawable.set_fishman3
        ),
        ChocoSet(
            title = "Железная дорога",
            listOf(
            ChocolateForm( chocolate = tastes[2], form = forms[5]),
            ChocolateForm( chocolate = tastes[2], form = forms[6]),
            ChocolateForm( chocolate = tastes[2], form = forms[7]),),
            imageId = R.drawable.set_trains
            ),
    )

    val testOrder = listOf(
        chocoSets[0],
        ChocolateForm(chocolate = tastes[0], form = forms[2]),
        ChocolateForm(chocolate = tastes[0], form = forms[1])
    )
}