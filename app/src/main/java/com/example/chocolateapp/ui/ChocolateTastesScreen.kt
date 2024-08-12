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