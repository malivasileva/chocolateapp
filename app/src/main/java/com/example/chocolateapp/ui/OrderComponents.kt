package com.example.chocolateapp.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable
import com.example.chocolateapp.ui.theme.ChocolateAppTheme


@Composable
fun OrderSetCard(
    chocoSet: ChocoSet,
    onChipClicked: (ChocolateForm, Chocolate) -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onDeleteSubButtonClicked: (ChocolateForm) -> Unit,
    onIncButton: (Orderable) -> Unit,
    onDecButton: (Orderable) -> Unit,
    modifier: Modifier = Modifier
) {
    Card {
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ){
            OrderItemContent(
                imageId = chocoSet.imageId,
                title = chocoSet.title,
                weight = chocoSet.weight,
                price = chocoSet._price,
                onChipClicked = { },
                onDeleteButtonClicked = { onDeleteButtonClicked() },
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small))
            )
            AmountCounter(
                amount = chocoSet.amount,
                onIncButton = { onIncButton(chocoSet) },
                onDecButton = { onDecButton(chocoSet) },
                modifier = Modifier.align(Alignment.Start)
            )
            Column {
                chocoSet.forms.forEach {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.padding_medium),
                            end = dimensionResource(id = R.dimen.padding_medium)
                        )
                    )
                    OrderSetItemContent(
                        item = it,
                        onChipClicked = { onChipClicked(it, it.chocolate!!) },
                        onDeleteButtonClicked = { onDeleteSubButtonClicked(it) }
                    )
                }
            }
        }
    }
}



@Composable
fun OrderSetItemContent(
    item: ChocolateForm,
    onChipClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.padding_small),
                bottom = dimensionResource(id = R.dimen.padding_small),
                end = dimensionResource(id = R.dimen.padding_small),
                start = dimensionResource(id = R.dimen.padding_large)
            )
    ){
//        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)))
        OrderItemContent (
            imageId = item.imageId,
            title = item.title,
            weight = item.weight,
            price = item.price,
            chocolate = item.chocolate?.title,
            onChipClicked = { onChipClicked() }, //todo
            onDeleteButtonClicked = { onDeleteButtonClicked() }
        )
    }
}

@Composable
fun OrderFormCard(
    item: ChocolateForm,
    onChipClicked: (Chocolate) -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onIncButton: (Orderable) -> Unit,
    onDecButton: (Orderable) -> Unit,
) {
    Card {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ){
            OrderItemContent(
                imageId = item.imageId,
                title = item.title,
                weight = item.weight,
                price = item._price,
                chocolate = item.chocolate?.title,
                onDeleteButtonClicked = { onDeleteButtonClicked() },
                onChipClicked = { onChipClicked(item.chocolate!!) }, //todo
            )
            AmountCounter(
                amount = item.amount,
                onIncButton = { onIncButton(item) },
                onDecButton = { onDecButton(item) })
        }
    }
}

@Composable
fun OrderItemContent (
    modifier: Modifier = Modifier,
    @DrawableRes imageId: Int,
    title: String,
    weight: Int,
    price: Int,
    chocolate: String? = null,
    onChipClicked: () -> Unit, //todo
    onDeleteButtonClicked: () -> Unit
) {
    Row (
        modifier = modifier
//            .background(Color.Cyan)
    ){
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.image_medium))
                .width(dimensionResource(id = R.dimen.image_medium))
                .clip(MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        Column (
            modifier = Modifier.weight(1f)
//                .fillMaxWidth()
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                Column (
                ){
                    Text(text = title)
                    Text(text = stringResource(id = R.string.weight, weight.toString()))
                }
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = stringResource(id = R.string.price, price.toString()),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = Modifier.align(alignment = Alignment.End)
                    )
                }
            }
            if (chocolate != null) {
                SuggestionChip(
                    onClick = { onChipClicked() }, //todo
                    label = { Text(text = chocolate) }
                )
            }
        }

        /*Spacer(modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .background(color = Color.Red))*/
        DeleteButton(
            onButtonClicked = { onDeleteButtonClicked() },
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }

}

@Composable
fun AmountCounter (
    amount: Int,
    onIncButton: () -> Unit,
    onDecButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ){
        Text(text = "Количество: ")
        IconButton(
            onClick = {
                onDecButton()
            },
//                    modifier = Modifier.size(24.dp)
            enabled = amount != 1,
        ) {
            Icon (
                imageVector = Icons.Outlined.KeyboardArrowLeft,
                contentDescription = "Уменьшить количество на 1"
            )
        }
        Text(text = amount.toString())
        IconButton(
            onClick = {
                onIncButton()
            },
        ) {
            Icon (
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "Увеличить количество на 1"
            )
        }
    }
}


@Composable
fun DeleteButton(
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onButtonClicked() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = stringResource(R.string.delete_from_cart)
        )
    }
}

@Composable
fun OrderActionsRow(
    totalPrice: Int,
    isButtonEnabled: Boolean,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth()

    ){
        val paddingSmall = dimensionResource(id = R.dimen.padding_small)
        Text(
            text = stringResource(R.string.total_price, totalPrice),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(start = paddingSmall),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        Button(
            onClick = { onButtonClicked() },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            enabled = isButtonEnabled,
            modifier = Modifier.padding(end = paddingSmall)
        ) {
            Text(text = stringResource(R.string.order_items))
        }
    }
}

@Preview
@Composable
fun OrderSetCardPreview() {
    ChocolateAppTheme {
        OrderSetCard(
            chocoSet = Datasource.chocoSets[0],
            onChipClicked = {chocoForm, chocolate ->},
            onDeleteButtonClicked = {},
            onDeleteSubButtonClicked = {},
            onIncButton = {item ->

            },
            onDecButton = {item ->

            },
        )
    }
}

@Preview
@Composable
fun OrderFormCardPreview() {
    ChocolateAppTheme {
        OrderFormCard(
            item = ChocolateForm(_chocolate = Datasource.tastes[0],
                form = Datasource.forms[0]),
            onChipClicked = {},
            onDeleteButtonClicked = {},
            onIncButton = {item ->},
            onDecButton = {item ->},
        )
    }
}

