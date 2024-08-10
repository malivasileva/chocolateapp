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