package com.example.chocolateapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.chocolateapp.ChocolateApplication
import com.example.chocolateapp.R
import com.example.chocolateapp.data.entity.ChocolateEntity
import com.example.chocolateapp.data.entity.Order
import com.example.chocolateapp.data.repository.ChocolateRepository
import com.example.chocolateapp.data.repository.ChocosetRepository
import com.example.chocolateapp.data.repository.FormRepository
import com.example.chocolateapp.data.repository.OrderRepository
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Form
import com.example.chocolateapp.model.Orderable
import com.example.chocolateapp.model.JsonOrderItem
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
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlin.math.roundToInt

class OrderViewModel(
    private val chocoRepo: ChocolateRepository,
    private val formRepo: FormRepository,
    private val setRepo: ChocosetRepository,
    private val orderRepo: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState(
//        number = 0,
        items = listOf(),
        totalPrice = 0,
        discount = 1f,
        chocolates = listOf<Chocolate>(),
        forms = listOf<Form>(),
        chocosets = listOf<ChocoSet>()
    ))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchForms()
        fetchChocoSets()
        fetchChocolates()

    }

    private fun fetchForms() {
        viewModelScope.launch (Dispatchers.IO) {
            formRepo.getAllForms().onEach {result ->
                when(result) {
                    is Resource.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                forms = result.data?.map { it.toForm() } ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                forms = result.data?.map { it.toForm() } ?: emptyList()
                            )
                        }
                        _eventFlow.emit(UIEvent.ShowSnackbar(
                            result.message ?: "Unknown error"
                        ))
                    }
                    is Resource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                forms = result.data?.map { it.toForm() } ?: emptyList()
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    private fun fetchChocoSets() {
        viewModelScope.launch(Dispatchers.IO) {
            setRepo.getAllChocosets().onEach {result ->
                when(result) {
                    is Resource.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                chocosets = result.data ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                chocosets = result.data ?: emptyList()
                            )
                        }
                        _eventFlow.emit(UIEvent.ShowSnackbar(
                            result.message ?: "Unknown error"
                        ))
                    }
                    is Resource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                chocosets = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
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
        val currentPrice = uiState.value.discount * _uiState.value.items.fold(0) {  sum, item ->
            sum + item._price
        }
        Log.d("govno111", uiState.value.discount.toString())
        Log.d("govno222", currentPrice.toString())
        _uiState.update { currentState ->
            currentState.copy(
                totalPrice = currentPrice.roundToInt()
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
                items = listOf(),
                totalPrice = 0
            )
        }
//        countTotalPrice()
//        _uiState.value = OrderUiState(number = 0, items = listOf(), totalPrice = 0, chocolates = chocolates)
    }

    suspend fun sendOrder(customerName: String, phone: String, description: String, type: String) : Int {
//        viewModelScope.launch {
            val jsonInfo = getListOfForms().map {
                Json.encodeToString(JsonOrderItem.serializer(), it.toJsonOrderItem())
            }
            return orderRepo.sendOrder(
                Order(
                    id = -1,
                    customerName = customerName,
                    phone = phone,
                    total = uiState.value.totalPrice,
                    info = jsonInfo.toString(),
                    description = description,
                    type = type
                )
            )
//        }
    }

    suspend fun applyPromocode(promocode: String) : Boolean {
        val discount = orderRepo.checkPromocode(promocode).toFloat() / 100f
        if (discount > 0) {
            _uiState.update {current ->
                current.copy(
                    discount = 1f - discount
                )
            }
            countTotalPrice()
            return true
        }
        return false
    }

    private fun getListOfForms() : List<ChocolateForm> {
        val result = mutableListOf<ChocolateForm>()
        uiState.value.items.forEach() {orderable ->
            if (orderable is ChocoSet) {
                orderable.forms.forEach {form ->
                    result.add(form.copy(isChocoset = true))
                }
            } else if (orderable is ChocolateForm) {
                result.add(orderable)
            }
        }
        return result
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
                    application.container.chocolateRepository,
                    application.container.formRepository,
                    application.container.setRepository,
                    application.container.orderRepository
                )
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}


data class OrderUiState (
//    val number: Int,
    val discount: Float,
    val items: List<Orderable>,
    val chocolates: List<Chocolate>,
    val forms: List<Form>,
    val chocosets: List<ChocoSet>,
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