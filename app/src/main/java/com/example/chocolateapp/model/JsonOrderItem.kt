package com.example.chocolateapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonOrderItem(
    @SerialName("form_id") val formId: Int,
    @SerialName("chocolate_id") val chocolateId: Int,
    val chocoset: Boolean,
    val count: Int
)
