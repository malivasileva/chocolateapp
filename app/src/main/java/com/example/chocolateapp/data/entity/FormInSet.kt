package com.example.chocolateapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Form_in_set")
data class FormInSet(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "set_id")
    @SerialName(value = "set_id")
    val setId: Int,
    @ColumnInfo(name = "form_id")
    @SerialName(value = "form_id")
    val formId: Int,
)
