package com.example.chocolateapp.data

import com.example.chocolateapp.R
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Form

/*object FormTable : IdTable<UUID> ("forms") {
    override val id = uuid("id").entityId()
    val title = varchar("title", 512).nullable()
    val weight = integer("weight")
}*/

//object ChocoFormTable : IdTable<UUID>("Choco")

/*object ChocoSetTable : IdTable<UUID> ("choco_sets") {
    override val id = uuid("id").entityId()
    val formsId = reference()
}*/

object Datasource {
    val tastes = listOf(
        Chocolate(title = "Молочный", price = 300, description = "Молочный шоколад", imageId = R.drawable.milk_chocolate),
        Chocolate(title = "Белый", price = 200, description = "Белый шоколад", imageId = R.drawable.white_chocolate),
        Chocolate(title = "Горький", price = 400, description = "Горький шоколад", imageId = R.drawable.dark_chocolate),
        Chocolate(title = "Кофейный", price = 400, description = "Кофейный шоколад", imageId = R.drawable.coffee_chocolate),
        Chocolate(title = "Карамельный", price = 400, description = "Карамельный шоколад", imageId = R.drawable.caramel_chocolate),
    )

    val forms = listOf(
        Form(title = "Слиток", weight = 1000, imageId = R.drawable.ingot, imgSrc = ""),
        Form(title = "Плитка", weight = 100, imageId = R.drawable.bar, imgSrc = "https://choco38.ru/img/bar.jpg"),
        Form(title = "Рыба 1", weight = 20, imageId = R.drawable.fish, imgSrc = ""),
        Form(title = "Лодка", weight = 40, imageId = R.drawable.boat, imgSrc = ""),
        Form(title = "Рыба 2", weight = 15, imageId = R.drawable.fish2, imgSrc = ""),
        Form(title = "Поезд 1", weight = 35, imageId = R.drawable.train1, imgSrc = ""),
        Form(title = "Поезд 2", weight = 35, imageId = R.drawable.train2, imgSrc = ""),
        Form(title = "Поезд 3", weight = 35, imageId = R.drawable.train3, imgSrc = ""),
    )

    val chocoSets = listOf(
        ChocoSet(
            title = "Рыбаку 1",
            forms = mutableListOf(
                ChocolateForm( _chocolate = null, form = forms[2], imgSrc = ""),
                ChocolateForm( _chocolate = null, form = forms[3], imgSrc = ""),
                ChocolateForm( _chocolate = null, form = forms[4], imgSrc = ""),),
            imageId = R.drawable.set_fishman1,
            imgSrc = ""
        ),
        ChocoSet(
            title = "Рыбаку 2",
            forms = mutableListOf(
                ChocolateForm( _chocolate = null, form = forms[2], imgSrc = ""),
                ChocolateForm( _chocolate = null, form = forms[3], imgSrc = ""),
                ChocolateForm( _chocolate = null, form = forms[4], imgSrc = ""),),
            imageId = R.drawable.set_fishman2,
            imgSrc = ""
        ),
        ChocoSet(
            title = "Рыбаку 3",
            forms = mutableListOf(
                ChocolateForm( _chocolate = null, form = forms[2], imgSrc = ""),
                ChocolateForm( _chocolate = null, form = forms[3], imgSrc = ""),
                ChocolateForm( _chocolate = null, form = forms[4], imgSrc = ""),),
            imageId = R.drawable.set_fishman3,
            imgSrc = ""
        ),
        ChocoSet(
            title = "Железная дорога",
            forms = mutableListOf(
                ChocolateForm( _chocolate = tastes[2], form = forms[5], imgSrc = ""),
                ChocolateForm( _chocolate = tastes[2], form = forms[6], imgSrc = ""),
                ChocolateForm( _chocolate = tastes[2], form = forms[7], imgSrc = "")),
            imageId = R.drawable.set_trains,
            imgSrc = ""
        ),
    )

    val testOrder = listOf(
        chocoSets[0],
        ChocolateForm(_chocolate = tastes[0], form = forms[2], imgSrc = ""),
        ChocolateForm(_chocolate = tastes[0], form = forms[1], imgSrc = "")
    )
}