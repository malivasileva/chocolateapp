package com.example.chocolateapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TasteBottomSheet (
    item: Orderable?,
    tastes: List<Chocolate>,
    @StringRes buttonTextId: Int,
    onDismissRequest: () -> Unit,
    onChipClicked: (Chocolate, ChocolateForm) -> Unit,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedChocolate by remember { mutableStateOf<Chocolate?>(null) }
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
            ) {
                if (item is ChocolateForm) {
//                    selectedChocolates = mutableMapOf(item to item.chocolate)
//                    selectedChocolates[item] = Chocolate(title = "test", price = 1)
                    ChocolateFormItem(
                        item = item,
                        tastes = tastes,
                        selectedChocolate = selectedChocolate,
                        onChipClicked = { chocolate ->
//                            Log.d("chip00", selectedChocolates[item].toString())
                            onChipClicked(chocolate, item)
                            selectedChocolate = chocolate
                            buttonState = true
                        }) //todo
                    Button (
                        enabled = buttonState,
                        shape = MaterialTheme.shapes.small,
                        content = { Text(text = stringResource(id = buttonTextId)) },
                        onClick = {
                            item.updateChocolate(selectedChocolate)
                            onButtonClicked() },
                        modifier = Modifier.align(Alignment.End)
                    )
                } else if (item is ChocoSet) {
                    /*item.forms.forEach { form ->
                        selectedChocolate[form] = form.chocolate
                    }
                    ChocoSetItem(
                        chocoSet = item,
                        tastes = tastes,
                        selectedChocolates = selectedChocolate,
                        onChipClicked = { chocolate, form ->
                            onChipClicked(chocolate, form)
                            selectedChocolate[form] = chocolate
                            if (selectedChocolate.values.all { it != null })
                            { buttonState = true }
                        } //todo
                    )
                    Button (
                        enabled = buttonState,
                        shape = MaterialTheme.shapes.small,
                        content = { Text(text = stringResource(id = buttonTextId)) },
                        onClick = {
                            item.forms.forEach { form ->
                                form.updateChocolate(selectedChocolate[form])
                            }
                            onButtonClicked()
                        },
                        modifier = Modifier.align(Alignment.End)
                    )*/
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
        } //no item chosen
    }
}

@Composable
fun ChocoSetItem (
    chocoSet: ChocoSet,
    tastes: List<Chocolate>,
    selectedChocolates: Chocolate?,
    onChipClicked: (Chocolate, ChocolateForm) -> Unit, //todo
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        ItemInfo(
            imageId = chocoSet.imageId,
            title = chocoSet.title,
            weight = chocoSet.weight
        )
        chocoSet.forms.forEach() {
            ChocolateFormItem(
                item = it,
                tastes = tastes,
                selectedChocolate = selectedChocolates,
                onChipClicked = { chocolate ->
                    onChipClicked(chocolate, it)
                } //todo
            )
        }
    }
}

@Composable
private fun ChocolateFormItem(
    item: ChocolateForm,
    tastes: List<Chocolate>,
    selectedChocolate: Chocolate?,
    onChipClicked: (Chocolate) -> Unit
) {
    Column {
        ItemInfo(
            imageId = item.imageId,
            title = item.title,
            weight = item.weight
        )
        ChipGroup(
            tastes = tastes,
            selectedChocolate = selectedChocolate,
            onChipClicked = { chocolate ->
//                Log.d("chip000", selectedChocolate.toString())
                onChipClicked(chocolate)
            }
        )
    }
}

@Composable
fun ItemInfo (
    @DrawableRes imageId: Int,
    title: String,
    weight: Int
) {
    Row (
    ){
        Image (
            painter = painterResource(id = imageId),
            contentDescription = null,
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
    onChipClicked: (Chocolate) -> Unit
) {
    FlowRow( //todo finish placing chips
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        tastes.forEach {
            FilterChip(
                label = { Text(text = it.title) },
                selected = it == selectedChocolate,
                onClick = {
//                    Log.d("chip0000", selectedChocolate.toString())
                    onChipClicked(it)
                }, //todo
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
//        selectedChocolates = mutableMapOf(),
        modifier = Modifier.fillMaxWidth()
    )
}