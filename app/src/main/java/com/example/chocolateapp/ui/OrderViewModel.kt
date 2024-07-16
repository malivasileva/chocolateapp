package com.example.chocolateapp.ui

import android.util.Log
import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import com.example.chocolateapp.data.Datasource
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

    private val _uiState = MutableStateFlow(OrderUiState(number = 0, items = listOf(), totalPrice = 0))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    private fun countTotalPrice() {
        val currentPrice = _uiState.value.items.fold(0) {  sum, item ->
            sum + item._price
        }
        _uiState.update { currentState ->
            currentState.copy(
                totalPrice = currentPrice
            )
        }
    }

    fun addItem(item: Orderable) {
        _uiState.update { currentState ->
            currentState.copy(
                items = _uiState.value.items + item
            )
        }
        countTotalPrice()
    }

    fun deleteItem(item: Orderable) {
        val tmp = _uiState.value.items.toMutableList()
        tmp.remove(item)
        _uiState.update {currentState ->
            currentState.copy (
                items = tmp
            )
        }
        countTotalPrice()
    }

    fun deleteSubItem(item: ChocoSet, form: ChocolateForm) {
        val tmp = _uiState.value.items.toMutableList()
        val index = tmp.indexOf(item)
        if (index != -1) {
            (tmp[index] as ChocoSet).removeForm(form)
        }
//        Log.d("chocoSet3", (tmp[index] as ChocoSet).forms.size.toString())
        _uiState.update {currentState ->
            currentState.copy (
                items = tmp
            )
        }
        countTotalPrice()
    }

    fun clearOrder() {
        _uiState.value = OrderUiState(number = 0, items = listOf(), totalPrice = 0)
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
        countTotalPrice()
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
        countTotalPrice()
    }

    fun increaseAmount (item: Orderable) {
        Log.d("ammmm1i", item.amount.toString())
        var tmp = _uiState.value.items
        val index = tmp.indexOf(item)
        tmp[index].incAmount()
        _uiState.update { currentState ->
            currentState.copy(
                items = tmp
            )
        }
        countTotalPrice()
        Log.d("ammmm2i", item.amount.toString())
    }

    fun decreaseAmount (item: Orderable) {
        Log.d("ammmm1d", item.amount.toString())
        var tmp = _uiState.value.items
        val index = tmp.indexOf(item)
        tmp[index].decAmount()
        _uiState.update { currentState ->
            currentState.copy(
                items = tmp
            )
        }
        countTotalPrice()
        Log.d("ammmm2d", item.amount.toString())
    }
}