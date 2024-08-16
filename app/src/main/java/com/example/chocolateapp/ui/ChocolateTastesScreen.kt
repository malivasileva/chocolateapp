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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.ui.theme.ChocolateAppTheme

@Composable
fun ChocolateTastesScreen (
    chocolates: List<Chocolate>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        contentPadding = contentPadding,
        modifier = modifier
    ){
        items (chocolates) {
            val paddingSmall = dimensionResource(id = R.dimen.padding_small)
            ChocolateCard(
                chocolate = it,
                modifier = Modifier.padding(paddingSmall)
            )
        }
    }
}

@Preview
@Composable
fun ChocolateTastesScreenPreview () {
    ChocolateAppTheme {
        ChocolateTastesScreen(
            Datasource.tastes,
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
        )
    }
}