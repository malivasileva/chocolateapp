package com.example.chocolateapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable
import com.example.chocolateapp.ui.theme.ChocolateAppTheme

@Composable
fun OrderScreen (
    items: List<Orderable>,
    onFormChipClicked: (Chocolate, ChocolateForm, ChocoSet?) -> Unit,
    onDeleteButtonClicked: (Orderable) -> Unit = {},
    onDeleteSubButtonClicked: (ChocoSet, ChocolateForm) -> Unit,
    onOrderButtonClicked: () -> Unit,
    onIncButton: (Orderable) -> Unit,
    onDecButton: (Orderable) -> Unit,
    totalPrice: Int = 0,
) {
    Column {
        LazyColumn (
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ){
            items (items) {
                if (it is ChocoSet) {
                    OrderSetCard(
                        chocoSet = it,
                        onChipClicked = { chocolate, formFromSet ->
                            onFormChipClicked(formFromSet, chocolate, it) },
                        onDeleteButtonClicked = { onDeleteButtonClicked(it) },
                        onDeleteSubButtonClicked = { form: ChocolateForm ->
                            onDeleteSubButtonClicked(it, form)
                        },
                        onIncButton = {item ->
                            onIncButton(item)
                        },
                        onDecButton = {item ->
                            onDecButton(item)
                        },
                    )
                }
                if (it is ChocolateForm) {
                    OrderFormCard(
                        item = it,
                        onChipClicked = { chocolate ->
                            onFormChipClicked(chocolate, it, null)
                        },
                        onDeleteButtonClicked = { onDeleteButtonClicked(it) },
                        onIncButton = {item ->
                            onIncButton(item)
                        },
                        onDecButton = {item ->
                            onDecButton(item)
                        },
                    )
                }
            }
        }
        OrderActionsRow(
            totalPrice = totalPrice,
            isButtonEnabled = items.isNotEmpty(),
            onButtonClicked = { onOrderButtonClicked() },
        )
    }
}

@Preview
@Composable
fun OrderScreenPreview () {
    ChocolateAppTheme {
        OrderScreen(
            onFormChipClicked = { chocolate, form, item ->

            },
            items = Datasource.testOrder,
            onDeleteButtonClicked = {},
            onDeleteSubButtonClicked = { chocoSet, chocoForm ->

            },
            onOrderButtonClicked = {},
            onIncButton = {item ->
//                onIncButton(item)
            },
            onDecButton = {item ->
//                onDecButton(item)
            },
        )
    }
}