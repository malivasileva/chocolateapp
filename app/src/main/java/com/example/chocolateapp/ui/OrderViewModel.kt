package com.example.chocolateapp.ui

import androidx.lifecycle.ViewModel
import com.example.chocolateapp.data.OrderUiState
import com.example.chocolateapp.model.Orderable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState(number = 0, summary = listOf()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun addItem(item: Orderable) {
        _uiState.update { currentState ->
            currentState.copy(
                summary = _uiState.value.summary + item
            )
        }
    }
}