package com.example.chocolateapp.data

import com.example.chocolateapp.R
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Form

object Datasource {
    val contactWays = listOf(
        "Telegram", "Viber", "WhatsUp", "По телефону"
    )


    val tastes = listOf(
        Chocolate(title = "Молочный", price = 300, description = "Молочный шоколад", imageId = R.drawable.default_chocolate),
        Chocolate(title = "Белый", price = 200, description = "Белый шоколад", imageId = R.drawable.default_chocolate),
        Chocolate(title = "Горький", price = 400, description = "Горький шоколад", imageId = R.drawable.default_chocolate),
        Chocolate(title = "Кофейный", price = 400, description = "Кофейный шоколад", imageId = R.drawable.default_chocolate),
        Chocolate(title = "Карамельный", price = 400, description = "Карамельный шоколад", imageId = R.drawable.default_chocolate),
    )

    val forms = listOf(
        Form(title = "Слиток", weight = 1000, imageId = R.drawable.default_chocolate, imgSrc = ""),
        Form(title = "Плитка", weight = 100, imageId = R.drawable.default_chocolate, imgSrc = "https://choco38.ru/img/bar.jpg"),
        Form(title = "Рыба 1", weight = 20, imageId = R.drawable.default_chocolate, imgSrc = ""),
        Form(title = "Лодка", weight = 40, imageId = R.drawable.default_chocolate, imgSrc = ""),
        Form(title = "Рыба 2", weight = 15, imageId = R.drawable.default_chocolate, imgSrc = ""),
        Form(title = "Поезд 1", weight = 35, imageId = R.drawable.default_chocolate, imgSrc = ""),
        Form(title = "Поезд 2", weight = 35, imageId = R.drawable.default_chocolate, imgSrc = ""),
        Form(title = "Поезд 3", weight = 35, imageId = R.drawable.default_chocolate, imgSrc = ""),
    )

    val chocoSets = listOf(
        ChocoSet(
            title = "Рыбаку 1",
            forms = mutableListOf(
                ChocolateForm( _chocolate = null, form = forms[2], imgSrc = "", id = -1),
                ChocolateForm( _chocolate = null, form = forms[3], imgSrc = "", id = -1),
                ChocolateForm( _chocolate = null, form = forms[4], imgSrc = "", id = -1),),
            imageId = R.drawable.default_chocolate,
            imgSrc = ""
        ),
        ChocoSet(
            title = "Рыбаку 2",
            forms = mutableListOf(
                ChocolateForm( _chocolate = null, form = forms[2], imgSrc = "", id = -1),
                ChocolateForm( _chocolate = null, form = forms[3], imgSrc = "", id = -1),
                ChocolateForm( _chocolate = null, form = forms[4], imgSrc = "", id = -1),),
            imageId = R.drawable.default_chocolate,
            imgSrc = ""
        ),
        ChocoSet(
            title = "Рыбаку 3",
            forms = mutableListOf(
                ChocolateForm( _chocolate = null, form = forms[2], imgSrc = "", id = -1),
                ChocolateForm( _chocolate = null, form = forms[3], imgSrc = "", id = -1),
                ChocolateForm( _chocolate = null, form = forms[4], imgSrc = "", id = -1),),
            imageId = R.drawable.default_chocolate,
            imgSrc = ""
        ),
        ChocoSet(
            title = "Железная дорога",
            forms = mutableListOf(
                ChocolateForm( _chocolate = tastes[2], form = forms[5], imgSrc = "", id = -1),
                ChocolateForm( _chocolate = tastes[2], form = forms[6], imgSrc = "", id = -1),
                ChocolateForm( _chocolate = tastes[2], form = forms[7], imgSrc = "", id = -1)),
            imageId = R.drawable.default_chocolate,
            imgSrc = ""
        ),
    )

    val testOrder = listOf(
        chocoSets[0],
        ChocolateForm(_chocolate = tastes[0], form = forms[2], imgSrc = "", id = -1),
        ChocolateForm(_chocolate = tastes[0], form = forms[1], imgSrc = "", id = -1)
    )
}