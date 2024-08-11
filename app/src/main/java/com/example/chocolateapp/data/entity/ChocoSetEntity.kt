package com.example.chocolateapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.ChocolateForm
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Chocoset")
data class ChocoSetEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    @SerialName(value = "img_src") val imgSrc: String,
) {
    fun toChocoSet(forms: List<ChocolateForm>) : ChocoSet {
        return ChocoSet(
            id = id,
            title = title,
            forms = forms.toMutableList(),
            imgSrc = "https://choco38.ru/static/img/" + imgSrc
        )
    }
}
