package com.example.chocolateapp.ui

import androidx.lifecycle.ViewModel
import com.example.chocolateapp.data.OrderUiState
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState(number = 0, items = listOf()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun addItem(item: Orderable) {
        _uiState.update { currentState ->
            currentState.copy(
                items = _uiState.value.items + item
            )
        }
    }

    fun deleteItem(item: Orderable) {
        val tmp = _uiState.value.items.toMutableList()
        tmp.remove(item)
        _uiState.update {currentState ->
            currentState.copy (
                items = tmp
            )
        }
    }

    fun deleteSubItem(item: ChocoSet, form: ChocolateForm) {
        val tmp = _uiState.value.items.toMutableList()
        tmp.indexOf(item).let {
            if (it != -1) {
                (tmp[it] as ChocoSet).forms.remove(form)
            }
        }
        _uiState.update { currentState ->
            currentState.copy(
                items = tmp
            )

        }
    }

    fun clearOrder() {
        _uiState.value = OrderUiState(number = 0, items = listOf())
    }

    fun setChocolate(item: ChocolateForm, chocolate: Chocolate) {
        val tmp = _uiState.value.items.toMutableList()
        tmp.indexOf(item).let {
            if (it != -1) {
                (tmp[it] as ChocolateForm).updateChocolate(chocolate)
            }
        }
        _uiState.update { currentState ->
            currentState.copy(
                items = tmp
            )
        }
    }

    fun setChocolate(item: ChocoSet, form: ChocolateForm, chocolate: Chocolate) {
        val tmp = _uiState.value.items.toMutableList()
        tmp.indexOf(item).let {
            if (it != -1) {
                val tmpSet = (tmp[it] as ChocoSet)
                tmpSet.forms.indexOf(form).let {
                    if (it != -1) {
                        tmpSet.forms[it].updateChocolate(chocolate)
                    }
                }
            }
        }
        _uiState.update { currentState ->
            currentState.copy(
                items = tmp
            )
        }
    }
}