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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.Form
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
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(form.imgSrc)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.error_chocolate),
                placeholder = painterResource(R.drawable.default_chocolate),
                contentDescription = "photo of chocolate",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .heightIn(max = dimensionResource(id = R.dimen.image_size))
                    .align(Alignment.CenterHorizontally))
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
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(chocoSet.imgSrc)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.error_chocolate),
                placeholder = painterResource(R.drawable.default_chocolate),
                contentDescription = stringResource(R.string.cd_photo, chocoSet.title),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .heightIn(max = dimensionResource(id = R.dimen.image_size))
                    .align(Alignment.CenterHorizontally))
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

