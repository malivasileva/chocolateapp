package com.example.chocolateapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.chocolateapp.ChocolateApplication
import com.example.chocolateapp.data.ChocolateEntity
import com.example.chocolateapp.data.ChocolateRepository
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable
import com.example.chocolateapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel(
    private val chocoRepo: ChocolateRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState(number = 0, items = listOf(), totalPrice = 0, chocolates = listOf<Chocolate>()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchChocolates()
    }

    private fun fetchChocolates() {
        viewModelScope.launch(Dispatchers.IO) {
            chocoRepo.getAllChocolates().onEach {result ->
                when(result) {
                    is Resource.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                chocolates = result.data?.map { it.toChocolate() } ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                chocolates = result.data?.map { it.toChocolate() } ?: emptyList()
                            )
                        }
                        _eventFlow.emit(UIEvent.ShowSnackbar(
                            result.message ?: "Unknown error"
                        ))
                    }
                    is Resource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                chocolates = result.data?.map { it.toChocolate() } ?: emptyList()
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }

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
        _uiState.update { currentState ->
            currentState.copy(
                items = listOf()
            )
        }
//        _uiState.value = OrderUiState(number = 0, items = listOf(), totalPrice = 0, chocolates = chocolates)
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

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ChocolateApplication)
                OrderViewModel(
                    application.container.chocolateRepository
                )
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}


data class OrderUiState (
    val number: Int,
    val items: List<Orderable>,
    val chocolates: List<Chocolate>,
    val totalPrice: Int
){
}

fun ChocolateEntity.toChocolate () : Chocolate {
    return Chocolate (
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
//        imageId = //todo
    )
}