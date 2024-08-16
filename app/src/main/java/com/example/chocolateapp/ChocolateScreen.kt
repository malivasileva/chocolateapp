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

package com.example.chocolateapp

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable
import com.example.chocolateapp.ui.ChocoSetScreen
import com.example.chocolateapp.ui.FormsScreen
import com.example.chocolateapp.ui.OrderScreen
import com.example.chocolateapp.ui.OrderViewModel
import com.example.chocolateapp.ui.TasteBottomSheet
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


sealed class ChocolateScreen (
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val iconId: Int
) {
    data object Set: ChocolateScreen("sets",R.string.sets, R.drawable.ic_set)
    data object Forms: ChocolateScreen("forms",R.string.forms, R.drawable.ic_chocolate)
    data object Order: ChocolateScreen("order",R.string.order, R.drawable.ic_cart)
}

val screens = listOf(
    ChocolateScreen.Forms,
    ChocolateScreen.Set,
    ChocolateScreen.Order
)

@Composable
fun ChocolateBottomNavBar(
    items: List<ChocolateScreen>,
    navController: NavHostController,
) {
    BottomNavigation (
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val currentScreenRoute = currentDestination?.route
        items.forEach {screen ->
            val selected = currentScreenRoute == screen.route
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true

                    }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = screen.iconId),
                        contentDescription = stringResource(id = screen.resourceId),
                        tint = if (selected) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.inversePrimary
                        },
                        modifier = Modifier.height(24.dp)
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChocolateTopBar () {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Choco38", style = MaterialTheme.typography.headlineMedium)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChocolateApp (
    viewModel: OrderViewModel = viewModel(factory = OrderViewModel.factory),
    navController: NavHostController = rememberNavController()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var snackBarJob: Job? by remember { mutableStateOf(null) }
    val successAddToCart = stringResource(id = R.string.snack_succesfully_added_to_cart)
    val successRemoveFromCart = stringResource(id = R.string.snack_succesfully_removed_from_cart)
    val successOrder = stringResource(id = R.string.snack_succesfully_ordered)
    val promocodeSuccess = stringResource(id = R.string.promocode_success)
    val promocodeFailMsg = stringResource(id = R.string.promocode_fail)

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Orderable?>(null) }
    val selectedChocolates = remember { mutableStateListOf<Chocolate?>(null)}

    var showEditBottomSheet by remember { mutableStateOf(false) }
    var selectedEditItem by remember { mutableStateOf<ChocolateForm?>(null) }
    var selectedChocoSet by remember { mutableStateOf<ChocoSet?>(null) }
    var selectedEditTaste by remember { mutableStateOf<Chocolate?>(null)}

    var promocodeFieldEnabled by remember { mutableStateOf(true) }
    var promocodeButtonEnabled by remember { mutableStateOf(true) }
    var promocode by remember { mutableStateOf("")}

    fun showSnackBar(message: String) {
        snackBarJob?.cancel()
        snackBarJob = scope.launch {
            snackBarHostState.showSnackbar(
                message = message,
                withDismissAction = true
            )
        }
    }

    Scaffold (
        topBar = {
            ChocolateTopBar()
        },
        bottomBar = {
            ChocolateBottomNavBar(
                items = screens,
                navController = navController,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
    ){ paddingValues ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = ChocolateScreen.Forms.route,
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) },
            popEnterTransition = { fadeIn(tween(300)) },
            popExitTransition = { fadeOut(tween(300)) },
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = ChocolateScreen.Forms.route) {
                FormsScreen(
                    forms = uiState.forms,
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
                    onButtonClicked = { chocolateForm ->
                        showBottomSheet = true
                        selectedItem = chocolateForm
                        selectedChocolates.clear()
                        selectedChocolates.add(null)
                    }
                )
            }
            composable(route = ChocolateScreen.Set.route) {
                ChocoSetScreen(
                    chocoSets = uiState.chocosets,
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
                    onButtonClicked = { chocolateSet ->

                        val forms = mutableListOf<ChocolateForm>()
                        chocolateSet.forms.forEach {
                            forms.add(it.clone() as ChocolateForm)
                        }
                        val copySet = ChocoSet(
                            title = chocolateSet.title,
                            imageId = chocolateSet.imageId,
                            forms = forms,
                            imgSrc = chocolateSet.imgSrc
                        )
                        showBottomSheet = true
                        selectedItem = copySet
                        selectedChocolates.clear()
                        (chocolateSet).forms.forEach {
                            selectedChocolates.add(null)
                        }
                    }
                )
            }
            composable(route = ChocolateScreen.Order.route) {
                OrderScreen(
                    items = uiState.items,
                    onFormChipClicked = { chocolate, chocolateForm, chocoSet: ChocoSet? ->
                        selectedChocoSet = chocoSet
                        selectedEditItem = chocolateForm
                        selectedEditTaste = chocolate
                        showEditBottomSheet = true
                    },
                    onDeleteButtonClicked = { item: Orderable ->
                        viewModel.deleteItem(uiState.items.indexOf(item))
                        showSnackBar(successRemoveFromCart)
                    },
                    onDeleteSubButtonClicked = { item: ChocoSet, form: ChocolateForm ->
                        viewModel.deleteSubItem(item, form)
                        showSnackBar(successRemoveFromCart)
                    },
                    onOrderButtonClicked = { name: String, phone: String, comment: String, type: String ->
                        scope.launch {
                            val code = viewModel.sendOrder(name, phone, comment, type)
                            viewModel.clearOrder()
                            if (code in (200..201)) {
                                showSnackBar(successOrder)
                                promocodeFieldEnabled = true
                                promocodeButtonEnabled = true
                                promocode = ""
                            }
                        }
                    },
                    totalPrice = uiState.totalPrice,
                    discount = uiState.discount,
                    onIncButton = {item ->
                        viewModel.increaseAmount(item)
                    },
                    onPromocodeButtonClicked = {
                        scope.launch{
                            val msg: String
                            if (viewModel.applyPromocode(promocode)) {
                                msg = promocodeSuccess
                                promocodeFieldEnabled = false
                                promocodeButtonEnabled = false
                            } else {
                                msg = promocodeFailMsg
                            }
                            showSnackBar(msg)
                        }
                    },
                    isPromocodeFieldEnable = promocodeFieldEnabled,
                    isPromocodeButtonEnable = promocodeButtonEnabled,
                    promocode = promocode,
                    onPromocodeChanged = {
                        promocode = it
                    },
                    onDecButton = {item ->
                        viewModel.decreaseAmount(item)
                    },

                )
            }
        }
        if (showBottomSheet) {
            TasteBottomSheet(
                item = selectedItem,
                tastes = uiState.chocolates,
                buttonTextId = R.string.add_to_cart,
                onDismissRequest = { showBottomSheet = false },
                onChipClicked = { chocolate: Chocolate, index: Int ->
                    selectedChocolates[index] = chocolate
                },
                onButtonClicked = {
                    if (selectedItem != null) {
                        if (selectedItem is ChocolateForm) (selectedItem as? ChocolateForm)?.updateChocolate(selectedChocolates.first())
                        if (selectedItem is ChocoSet) {
                            (selectedItem as? ChocoSet)?.updateChocolates(selectedChocolates)
                        }
                        val copyItem = selectedItem!!.clone()
                        viewModel.addItem(copyItem as Orderable)
                    }


                    showSnackBar(successAddToCart)
                    showBottomSheet = false

                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (showEditBottomSheet) {
            TasteBottomSheet(
                item = selectedEditItem,
                tastes = uiState.chocolates,
                buttonTextId = R.string.change,
                onDismissRequest = { showEditBottomSheet = false },
                onChipClicked = { chocolate, _ ->
                    selectedEditTaste = chocolate
                },
                onButtonClicked = {
                    if (selectedEditItem != null) {
                        if (selectedChocoSet != null) {
                            selectedChocoSet?.updateSubItem(selectedEditItem!!, selectedEditTaste!!)
                            viewModel.setChocolate(selectedEditItem!!, selectedEditTaste!!)
                        } else {
                            selectedEditItem?.updateChocolate(selectedEditTaste)
                            viewModel.setChocolate(selectedEditItem!!, selectedEditTaste!!)
                        }
                        showEditBottomSheet = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}



@Preview
@Composable
fun ChocolateAppPreview() {
    ChocolateApp()
}