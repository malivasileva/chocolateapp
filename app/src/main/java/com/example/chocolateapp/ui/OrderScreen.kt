/*
 * Copyright (C) 2024 Maria Vasileva
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.example.chocolateapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
    onOrderButtonClicked: (String, String, String, String) -> Unit,
    isPromocodeFieldEnable: Boolean,
    isPromocodeButtonEnable: Boolean,
    onPromocodeButtonClicked: () -> Unit,
    promocode: String,
    onPromocodeChanged: (String) -> Unit,
    onIncButton: (Orderable) -> Unit,
    onDecButton: (Orderable) -> Unit,
    totalPrice: Int = 0,
    discount: Float,
) {
    var showDialog by remember { mutableStateOf(false) }
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
            discount = discount,
            isButtonEnabled = items.isNotEmpty(),
            isPromocodeFieldEnabled = isPromocodeFieldEnable,
            isPromocodeButtonEnable = isPromocodeButtonEnable,
            onPromocodeButtonClicked = {
                onPromocodeButtonClicked()
            },
            promocode = promocode,
            onPromocodeChanged = {
                onPromocodeChanged(it)
            },
            onOrderButtonClicked = {
                showDialog = true
                              },
        )
    }
    if (showDialog) {
        OrderDialog(
            onDismissRequest = {
                showDialog = false
            },
            price = totalPrice,
            weight = items.fold(0){ sum, item ->
                sum + item.weight
            },
            onOrderButtonClicked = { name, phone, comment, type ->
                onOrderButtonClicked(name, "+7$phone", comment, type)
                showDialog = false
            }

        )
    }
}

@Composable
fun OrderDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onOrderButtonClicked: (String, String, String, String) -> Unit,
    price: Int,
    weight: Int
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card (
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column (
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(R.string.order_form_headline),
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it.capitalizeFirstLetter() },
                    label = {
                        Text(text = stringResource(R.string.your_name))
                    },
                    shape = MaterialTheme.shapes.small,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        if (it.length < 11) {
                            phone = it
                        }
                                    },
                    label = {
                        Text(text = stringResource(R.string.phone_number))
                    },
                    prefix = {
                        Text(text = "+7")
                    },
                    shape = MaterialTheme.shapes.small,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(
                            bottom = dimensionResource(id = R.dimen.padding_small)
                        )
                        .fillMaxWidth()
                )
                Text(
                    text = stringResource(R.string.choose_way_to_contact),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_small)
                    )
                )
                ChipGroup(
                    contactOptions = Datasource.contactWays,
                    selectedOption = selectedOption,
                    onChipClicked = {
                        selectedOption = it
                    },
                    modifier = Modifier.padding(
                        bottom = dimensionResource(id = R.dimen.padding_small)
                    )
                )
                OutlinedTextField(
                    value = comment,
                    onValueChange = {comment = it},
                    label = {
                        Text(text = stringResource(R.string.comment_field))
                    },
                    shape = MaterialTheme.shapes.small,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    minLines = 2,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(
                            bottom = dimensionResource(id = R.dimen.padding_small)
                        )
                        .fillMaxWidth()
                )
                Text(
                    text = stringResource(id = R.string.total_price, price),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = stringResource(id = R.string.total_weight, weight),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = dimensionResource(id = R.dimen.padding_small)
                    )
                )
                Text(
                    text = stringResource(R.string.personal_data_agreement),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(
                            bottom = dimensionResource(id = R.dimen.padding_small)
                        )

                )
                Button(
                    shape = MaterialTheme.shapes.small,
                    enabled = (
                            name.isNotEmpty()
                                    && phone.length == 10
                                    && selectedOption != ""
                            ),
                    onClick = {
                        onOrderButtonClicked(name, phone, comment, selectedOption)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = dimensionResource(id = R.dimen.padding_small)
                        )
                ) {
                    Text(text = stringResource(R.string.accept))
                }
                OutlinedButton(
                    shape = MaterialTheme.shapes.small,
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.back))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChipGroup(
    contactOptions: List<String>,
    selectedOption: String,
    onChipClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        contactOptions.forEach {
            FilterChip(
                label = { Text(text = it) },
                selected = it == selectedOption,
                onClick = {
                    onChipClicked(it)
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Preview
@Composable
fun DialogPreview () {
    ChocolateAppTheme {
        OrderDialog(
            onDismissRequest = {

            },
            price = 1234,
            weight = 567,
            onOrderButtonClicked = { name, phone, comment, type ->

            }

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
            onOrderButtonClicked = {a, b, c, d ->},
            onIncButton = {item ->
            },
            onPromocodeButtonClicked = {},
            isPromocodeFieldEnable = true,
            isPromocodeButtonEnable = true,
            promocode = "",
            onPromocodeChanged = {},
            onDecButton = {item ->
            },
            discount = 1f
        )
    }
}

fun String.capitalizeFirstLetter(): String {
    return if (this.isNotEmpty()) {
        this.substring(0, 1).uppercase() + this.substring(1)
    } else {
        this
    }
}