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
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Form
import com.example.chocolateapp.ui.theme.ChocolateAppTheme

@Composable
fun FormsScreen (
    forms: List<Form>,
    contentPadding: PaddingValues,
    onButtonClicked: (ChocolateForm) -> Unit
) {
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    LazyVerticalStaggeredGrid (
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = paddingSmall,
        horizontalArrangement = Arrangement.spacedBy(paddingSmall),
        contentPadding = contentPadding
    ) {
        items(forms) {
            FormCard (
                form = it,
                onButtonClicked = {
                    onButtonClicked(
                        ChocolateForm(form = it, _chocolate = null, imgSrc = it.imgSrc, id = it.id)
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun FormsScreenPreview () {
    ChocolateAppTheme {
        FormsScreen(
            forms = Datasource.forms,
            PaddingValues(dimensionResource(id = R.dimen.padding_small)),
            onButtonClicked = {}
        )
    }
}