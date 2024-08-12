package com.example.chocolateapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable
import com.example.chocolateapp.ui.theme.ChocolateAppTheme
import kotlin.math.roundToInt


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
                imgSrc = chocoSet.imgSrc,
                title = chocoSet.title,
                weight = chocoSet.weight,
                onChipClicked = { },
                onDeleteButtonClicked = { onDeleteButtonClicked() },
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small))
            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                AmountCounter(
                    amount = chocoSet.amount,
                    onIncButton = { onIncButton(chocoSet) },
                    onDecButton = { onDecButton(chocoSet) },
                )
                Text(
                    text = stringResource(id = R.string.price, chocoSet._price.toString()),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.padding_medium))
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
        OrderItemContent (
            imgSrc = item.imgSrc,
            title = item.title,
            weight = item.weight,
            chocolate = item.chocolate?.title,
            onChipClicked = { onChipClicked() },
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
                imgSrc = item.imgSrc,
                title = item.title,
                weight = item.weight,
                chocolate = item.chocolate?.title,
                onDeleteButtonClicked = { onDeleteButtonClicked() },
                onChipClicked = { onChipClicked(item.chocolate!!) },
            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                AmountCounter(
                    amount = item.amount,
                    onIncButton = { onIncButton(item) },
                    onDecButton = { onDecButton(item) }
                )
                Text(
                    text = stringResource(id = R.string.price, item._price.toString()),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    fontSize = 24.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }
    }
}

@Composable
fun OrderItemContent (
    modifier: Modifier = Modifier,
    imgSrc: String,
    title: String,
    weight: Int,
    chocolate: String? = null,
    onChipClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit
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
            contentDescription = stringResource(R.string.cd_set_photo, title),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.image_medium))
                .width(dimensionResource(id = R.dimen.image_medium))
                .clip(MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        Column (
            modifier = Modifier.weight(1f)
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column (
                ){
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(text = stringResource(id = R.string.weight, weight.toString()))
                }
            }
            if (chocolate != null) {
                SuggestionChip(
                    onClick = { onChipClicked() },
                    label = { Text(text = chocolate) }
                )
            }
        }
        DeleteButton(
            onButtonClicked = { onDeleteButtonClicked() },
            modifier = Modifier.align(Alignment.Top)
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
        modifier = modifier.wrapContentWidth()
    ){
        Text(text = stringResource(R.string.amount))
        IconButton(
            onClick = {
                onDecButton()
            },
            enabled = amount != 1,
        ) {
            Icon (
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.decrease_by_one)
            )
        }
        Text(text = amount.toString())
        IconButton(
            onClick = {
                onIncButton()
            },
        ) {
            Icon (
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = stringResource(R.string.increase_by_one)
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
    discount: Float,
    promocode: String,
    onPromocodeChanged: (String) -> Unit,
    isButtonEnabled: Boolean,
    isPromocodeFieldEnabled: Boolean,
    isPromocodeButtonEnable: Boolean,
    onOrderButtonClicked: () -> Unit,
    onPromocodeButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    val keyboardController = LocalSoftwareKeyboardController.current
    Column (
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = paddingSmall)
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = paddingSmall)
        ){
            OutlinedTextField(
                value = promocode,
                onValueChange = { onPromocodeChanged(it) },
                singleLine = true,
                enabled = isPromocodeFieldEnabled,
                visualTransformation = {
                    TransformedText(text = it.toUpperCase(), offsetMapping = OffsetMapping.Identity)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        keyboardController?.hide()
                        onPromocodeButtonClicked()
                    }
                ),
                label = {
                    Text(text = "Промокод")
                },
                modifier = Modifier.weight(1.5f)
            )
            Button(
                onClick = {
                    keyboardController?.hide()
                    onPromocodeButtonClicked()
                          },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                enabled = (promocode != ""
                        && isPromocodeButtonEnable),
                modifier = Modifier
                    .padding(
                        top = paddingSmall,
                        start = paddingSmall,
                    )
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = stringResource(R.string.apply),
                    maxLines = 1,
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    top = paddingSmall,
                    bottom = paddingSmall
                )

        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1.5f)
            ){
                Text(
                    text = stringResource(R.string.total_price, totalPrice),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                if (discount != 1f) {
                    var fullPrice = (totalPrice / discount).roundToInt()
                    fullPrice = if (fullPrice % 10 >= 5) {
                        ((fullPrice / 10) + 1) * 10
                    } else {
                        (fullPrice / 10) * 10
                    }
                    Text(
                        text = stringResource(id = R.string.price, fullPrice),
                        textDecoration = TextDecoration.LineThrough,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Button(
                onClick = { onOrderButtonClicked() },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                enabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = paddingSmall)
            ) {
                Text(text = stringResource(R.string.order_items))
            }
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
                form = Datasource.forms[0], imgSrc = "", id = -1),
            onChipClicked = {},
            onDeleteButtonClicked = {},
            onIncButton = {item ->},
            onDecButton = {item ->},
        )
    }
}

