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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chocolateapp.R
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.ui.theme.ChocolateAppTheme

@Composable
fun ChocoSetScreen (
    chocoSets: List<ChocoSet>,
    contentPadding: PaddingValues,
    onButtonClicked: (ChocoSet) -> Unit
) {
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    LazyVerticalStaggeredGrid (
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = paddingSmall,
        horizontalArrangement = Arrangement.spacedBy(paddingSmall),
        contentPadding = contentPadding
    ) {
        items(chocoSets) {
            SetCard (
                chocoSet = it,
                onButtonClicked = { onButtonClicked( it ) }
            )
        }
    }
}




@Preview
@Composable
fun SetScreenPreview () {
    ChocolateAppTheme {
        ChocoSetScreen(
            chocoSets = Datasource.chocoSets,
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
            onButtonClicked = {}
        )
    }
}