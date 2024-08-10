package com.example.chocolateapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Form_in_set")
data class FormInSet(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "set_id")
    val setId: Int,
    @ColumnInfo(name = "form_id")
    val formId: Int,
)
