package com.example.chocolateapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.chocolateapp.ui.theme.ChocolateAppTheme

@Composable
fun FormsScreen (title: String) {
    Text(text = title)
}

@Preview
@Composable
fun FormsScreenPreview () {
    ChocolateAppTheme {
        ChocolateTastesScreen(title = "Формы шоколада")
    }
}