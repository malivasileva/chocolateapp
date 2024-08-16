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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasteBottomSheet (
    item: Orderable?,
    tastes: List<Chocolate>,
    @StringRes buttonTextId: Int,
    onDismissRequest: () -> Unit,
    onChipClicked: (Chocolate, Int) -> Unit,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedChocolates = remember { mutableStateListOf<Chocolate?>() }
    selectedChocolates.clear()
    var buttonState by remember { mutableStateOf(false) }
    ModalBottomSheet(
        shape = MaterialTheme.shapes.medium,
        windowInsets = WindowInsets.displayCutout,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        onDismissRequest = { onDismissRequest() },
        modifier = modifier
    ) {
        if (item != null) {
            Column (
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                if (item is ChocolateForm) {
                    selectedChocolates.add(null)
                    ChocolateFormItem(
                        item = item,
                        tastes = tastes,
                        selectedChocolate = selectedChocolates.first() ,
                        onChipClicked = { chocolate ->
                            onChipClicked(chocolate, 0)
                            selectedChocolates[0] = chocolate
                            buttonState = true
                        })
                    Button (
                        enabled = buttonState,
                        shape = MaterialTheme.shapes.small,
                        content = { Text(text = stringResource(id = buttonTextId)) },
                        onClick = {
                            item.updateChocolate(selectedChocolates.first())
                            onButtonClicked() },
                        modifier = Modifier.align(Alignment.End)
                    )
                } else if (item is ChocoSet) {
                    repeat(item.forms.size) {
                        selectedChocolates.add(null)
                    }
                    ChocoSetItem(
                        chocoSet = item,
                        tastes = tastes,
                        selectedChocolates = selectedChocolates,
                        onChipClicked = { chocolate, index ->
                            onChipClicked(chocolate, index)
                            selectedChocolates[index] = chocolate
                            if (selectedChocolates.all { it != null })
                            { buttonState = true }
                        }
                    )
                    Button (
                        enabled = buttonState,
                        shape = MaterialTheme.shapes.small,
                        content = { Text(text = stringResource(id = buttonTextId)) },
                        onClick = {
                            item.forms.forEach { form ->
                                val index = item.forms.indexOf(form)
                                form.updateChocolate(selectedChocolates[index])
                            }
                            onButtonClicked()
                        },
                        modifier = Modifier.align(Alignment.End)
                    )
                }
                Spacer(modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                )
            }
        } else {
            Text(
                text = stringResource(R.string.nothing_choosed),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
            )
        }
    }
}

@Composable
fun ChocoSetItem (
    chocoSet: ChocoSet,
    tastes: List<Chocolate>,
    selectedChocolates: MutableList<Chocolate?>,
    onChipClicked: (Chocolate, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        ItemInfo(
            imgSrc = chocoSet.imgSrc,
            title = chocoSet.title,
            weight = chocoSet.weight,
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.padding_small),
                    bottom = dimensionResource(id = R.dimen.padding_small))
        )
        chocoSet.forms.zip(selectedChocolates).forEach() { (form: ChocolateForm, selectedChocolate: Chocolate?) ->
            Divider(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ChocolateFormItem(
                item = form,
                tastes = tastes,
                selectedChocolate = selectedChocolate,
                onChipClicked = { chocolate ->
                    onChipClicked(chocolate, chocoSet.forms.indexOfFirst { it === form })
                },
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_small),
                    top = dimensionResource(id = R.dimen.padding_small),
                    bottom = dimensionResource(id = R.dimen.padding_small)
                )
            )
        }
    }
}

@Composable
private fun ChocolateFormItem(
    item: ChocolateForm,
    tastes: List<Chocolate>,
    selectedChocolate: Chocolate?,
    onChipClicked: (Chocolate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        ItemInfo(
            imgSrc = item.imgSrc,
            title = item.title,
            weight = item.weight
        )
        ChipGroup(
            tastes = tastes,
            selectedChocolate = selectedChocolate,
            onChipClicked = { chocolate ->
                onChipClicked(chocolate)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ItemInfo (
    imgSrc: String,
    title: String,
    weight: Int,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
    ){
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imgSrc)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.error_chocolate),
            placeholder = painterResource(R.drawable.default_chocolate),
            contentDescription = "Изображение товара $title",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.image_small))
                .width(dimensionResource(id = R.dimen.image_small))
                .clip(MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.weight, weight.toString()),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
private fun ChipGroup(
    tastes: List<Chocolate>,
    selectedChocolate: Chocolate?,
    onChipClicked: (Chocolate) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        tastes.forEach {
            FilterChip(
                label = { Text(text = it.title) },
                selected = it == selectedChocolate,
                onClick = {
                    onChipClicked(it)
                },
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}


@Preview
@Composable
fun BottomSheetPreview() {
    TasteBottomSheet(
        item = Datasource.chocoSets[0],
        tastes = Datasource.tastes,
        buttonTextId = R.string.add_to_cart,
        onDismissRequest = { },
        onChipClicked = { chocolate, chocolateForm ->
        },
        onButtonClicked = { },
        modifier = Modifier.fillMaxWidth()
    )
}