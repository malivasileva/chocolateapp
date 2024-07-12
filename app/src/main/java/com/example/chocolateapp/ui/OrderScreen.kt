package com.example.chocolateapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable
import com.example.chocolateapp.ui.theme.ChocolateAppTheme

@Composable
fun OrderScreen (
    title: String,
    items: List<Orderable>,
    onDeleteButtonClicked: () -> Unit = {} //TODO
) {
    Column {
        LazyColumn (
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ){
            items (items) {
                if (it is ChocoSet) {
                    OrderSetCard(chocoSet = it, onDeleteButtonClicked = { onDeleteButtonClicked() })
                }
                if (it is ChocolateForm) {
                    OrderFormCard(item = it, onDeleteButtonClicked = { onDeleteButtonClicked() })
                }
            }
        }
        OrderActionsRow(
            totalPrice = items.fold (0) { sum, item ->
                sum + item.price
            },
            onButtonClicked = { /*TODO*/ },
//            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Preview
@Composable
fun OrderScreenPreview () {
    ChocolateAppTheme {
        OrderScreen(
            title = "Заказ",
            items = Datasource.testOrder
        )
    }
}