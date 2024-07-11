package com.example.chocolateapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.chocolateapp.ui.theme.ChocolateAppTheme

@Composable
fun OrderScreen (title: String) {
    Text(text = title)
}

@Preview
@Composable
fun OrderScreenPreview () {
    ChocolateAppTheme {
        ChocolateTastesScreen(title = "Заказ")
    }
}