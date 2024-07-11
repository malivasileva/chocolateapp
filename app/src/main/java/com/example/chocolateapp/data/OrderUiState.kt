package com.example.chocolateapp.data

import com.example.chocolateapp.model.Orderable

data class OrderUiState (
    val number: Int,
    val summary: List<Orderable>,
){
}