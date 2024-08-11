package com.example.chocolateapp.data.entity

import androidx.compose.ui.Modifier
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chocolateapp.model.Form
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "form")
data class FormEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val weight: Int,
    @SerialName(value = "img_src") val imgSrc: String
) {
    fun toForm() : Form {
        return Form(
            id = id,
            title = title,
            weight = weight,
            imgSrc = "https://choco38.ru/static/img/" + imgSrc
        )
    }
}


