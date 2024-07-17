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
        if (isInList(item)) {
            val tmp = _uiState.value.items.toMutableList()
            var existedItem: Orderable? = null
            if (item is ChocoSet) {
                existedItem = tmp.find {
                    it is ChocoSet &&
                    it.equalsInContent(item)
                }
            } else if (item is ChocolateForm) {
                existedItem = tmp.find {
                    it is ChocolateForm &&
                    it.equalsInContent(item)
                }
            }

            if (existedItem != null) {
                increaseAmount(existedItem)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    items = _uiState.value.items + item
                )
            }
        }
        countTotalPrice()
    }

    fun deleteItem(index: Int) {
        val tmp = _uiState.value.items.toMutableList()
        tmp.removeAt(index)
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
        val tmp = _uiState.value.items
        val index = tmp.indexOf(item)
        tmp[index].incAmount()
        _uiState.update { currentState ->
            currentState.copy(
                items = tmp
            )
        }
        countTotalPrice()
    }

    fun decreaseAmount (item: Orderable) {
        val tmp = _uiState.value.items
        val index = tmp.indexOf(item)
        tmp[index].decAmount()
        _uiState.update { currentState ->
            currentState.copy(
                items = tmp
            )
        }
        countTotalPrice()
    }

    private fun isInList(item: Orderable): Boolean {
        val tmp = _uiState.value.items
        var result = false
        if (item is ChocoSet) {
            val chocoSetList = tmp.filterIsInstance<ChocoSet>()
            result = chocoSetList.any {
                it.equalsInContent(item)
            }
        } else if (item is ChocolateForm) {
            result = tmp.any {
                it is ChocolateForm &&
                        it.chocolate == item.chocolate &&
                        it.form == item.form
            }
        }
        return result
    }

    /*fun getItemFormList(item: Orderable): Orderable? {
        val tmp = _uiState.value.items
        var result: Orderable? = null
        if (item is ChocoSet) {
            val chocoSet = item as ChocoSet
            val chocoSetList = tmp.filter { it is ChocoSet }
            val result = chocoSetList.find {it: Orderable ->
                (it as ChocoSet).equalsInContent(item)
            }
        } else if (item is ChocolateForm) {
            val chocoForm = item as ChocolateForm
            val result = tmp.find {
                it is ChocolateForm &&
                        (it as ChocolateForm).chocolate == chocoForm.chocolate &&
                        (it as ChocolateForm).form == chocoForm.form
            }
        }
        return result
    }*/
}