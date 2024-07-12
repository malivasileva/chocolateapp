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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.Form
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable
import com.example.chocolateapp.ui.theme.ChocolateAppTheme

@Composable
fun ChocolateCard(
    chocolate: Chocolate,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
    ){
        Column {
            Image(
                painter = painterResource(id = chocolate.imageId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column (
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = chocolate.title,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.price_grams, chocolate.price.toString()),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Text(
                    text = chocolate.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun FormCard(
    form: Form,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ){
        Column {
            Image(
                painter = painterResource(id = form.imageId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .heightIn(max = dimensionResource(id = R.dimen.image_size))
            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = form.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.weight, form.weight.toString()),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Button(
                onClick = { onButtonClicked() },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small) - 4.dp
                    )
            ) {
                Text(text = stringResource(R.string.add_to_cart))
            }
        }
    }
}

@Composable
fun SetCard(
    chocoSet: ChocoSet,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
    ){
        Column {
            Image(
                painter = painterResource(id = chocoSet.imageId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .heightIn(max = dimensionResource(id = R.dimen.image_size))
            )
            Row (
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = chocoSet.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = stringResource(id = R.string.weight, chocoSet.weight.toString()),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    modifier = Modifier
                        .wrapContentWidth()
                )
            }
            Button(
                onClick = { onButtonClicked() },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small) - 4.dp
                    )
            ) {
                Text(text = stringResource(R.string.add_to_cart))
            }
        }
    }
}

@Composable
fun OrderSetCard(
    chocoSet: ChocoSet,
    onDeleteButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                OrderItemContent(
                    imageId = chocoSet.imageId,
                    title = chocoSet.title,
                    weight = chocoSet.weight,
                    price = chocoSet.price,
                    onDeleteButtonClicked = { onDeleteButtonClicked() },
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
                )
            }
            Column {
                chocoSet.forms.forEach {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.padding_medium),
                            end = dimensionResource(id = R.dimen.padding_medium)
                        )
                    )
                    OrderSetItemCard(item = it, onDeleteButtonClicked = { onDeleteButtonClicked() })
                }
            }
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
fun OrderSetItemCard(
    item: ChocolateForm,
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
            onDeleteButtonClicked = { onDeleteButtonClicked() }
        )
    }
}


@Composable
fun OrderItemContent (
    @DrawableRes imageId: Int,
    title: String,
    weight: Int,
    price: Int,
    onDeleteButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
    ){
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.image_small))
                .width(dimensionResource(id = R.dimen.image_small))
                .clip(MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        Column (
            modifier = Modifier.weight(1f)
        ){
            Text(text = title)
            Text(text = stringResource(id = R.string.weight, weight.toString()))
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        Text(
            text = stringResource(id = R.string.price, price.toString()),
            maxLines = 1,
            //            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        DeleteButton(
            onButtonClicked = { onDeleteButtonClicked() },
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
@Composable
fun OrderFormCard(
    item: ChocolateForm,
    onDeleteButtonClicked: () -> Unit
) {
    Card {
        OrderItemContent(
            imageId = item.imageId,
            title = item.title,
            weight = item.weight,
            price = item.price,
            onDeleteButtonClicked = { onDeleteButtonClicked() },
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
    }
}

@Composable
fun OrderActionsRow(
    totalPrice: Int,
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
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
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
        OrderSetCard(chocoSet = Datasource.chocoSets[0], onDeleteButtonClicked = {})
    }
}

@Preview
@Composable
fun OrderFormCardPreview() {
    ChocolateAppTheme {
        OrderFormCard(
            item = ChocolateForm(chocolate = Datasource.tastes[0], form = Datasource.forms[0]),
            onDeleteButtonClicked = {}
        )
    }
}



@Preview
@Composable
fun ChocolateCardPreview() {
    ChocolateAppTheme {
        ChocolateCard(chocolate = Datasource.tastes[0])
    }
}

@Preview
@Composable
fun FormCardPreview() {
    ChocolateAppTheme {
        FormCard(form = Datasource.forms[0], onButtonClicked = {})
    }
}

@Preview
@Composable
fun SetCardPreview() {
    ChocolateAppTheme {
        SetCard(chocoSet = Datasource.chocoSets[0], onButtonClicked = {})
    }
}