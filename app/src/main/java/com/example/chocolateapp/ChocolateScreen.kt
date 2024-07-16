package com.example.chocolateapp

import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chocolateapp.data.Datasource
import com.example.chocolateapp.model.ChocoSet
import com.example.chocolateapp.model.Chocolate
import com.example.chocolateapp.model.ChocolateForm
import com.example.chocolateapp.model.Orderable
import com.example.chocolateapp.ui.FormsScreen
import com.example.chocolateapp.ui.OrderScreen
import com.example.chocolateapp.ui.OrderViewModel
import com.example.chocolateapp.ui.ChocoSetScreen
import com.example.chocolateapp.ui.TasteBottomSheet


sealed class ChocolateScreen (
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val iconId: Int
) {
    //    object Taste: ChocolateScreen("taste", R.string.chocolate, R.drawable.ic_chocolate)
    object Set: ChocolateScreen("sets",R.string.sets, R.drawable.ic_set)
    object Forms: ChocolateScreen("forms",R.string.forms, R.drawable.ic_chocolate)
    object Order: ChocolateScreen("order",R.string.order, R.drawable.ic_cart)
}

val screens = listOf(
//    ChocolateScreen.Taste,
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
            // currentDestination?.hierarchy?.any { it.route == screen.route } == true
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
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = currentDestination?.route

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Orderable?>(null) }
    var selectedChocolates = remember { mutableStateListOf<Chocolate?>(null)}

    var showEditBottomSheet by remember { mutableStateOf(false) }
    var selectedEditItem by remember { mutableStateOf<ChocolateForm?>(null) }
    var selectedChocoSet by remember { mutableStateOf<ChocoSet?>(null) }
    var selectedEditTaste by remember { mutableStateOf<Chocolate?>(null)}

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
                    forms = Datasource.forms,
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
                    onButtonClicked = { chocolateForm ->
                        showBottomSheet = true
                        selectedItem = chocolateForm
                        selectedChocolates.clear()
                        selectedChocolates.add(null)
                    } //todo
                )
            }
            composable(route = ChocolateScreen.Set.route) {
                ChocoSetScreen(
                    chocoSets = Datasource.chocoSets,
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
                    onButtonClicked = { chocolateSet ->
                        showBottomSheet = true
                        selectedItem = chocolateSet
                        selectedChocolates.clear()
                        (chocolateSet as ChocoSet).forms.forEach {
                            selectedChocolates.add(null)
                        }
                    } //todo
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
                        viewModel.deleteItem(item)
                    },
                    onDeleteSubButtonClicked = { item: ChocoSet, form: ChocolateForm ->
                        viewModel.deleteSubItem(item, form)
                    },
                    onOrderButtonClicked = {
                        viewModel.clearOrder()
                    }

                )
            }
        }
        if (showBottomSheet) {
            TasteBottomSheet(
                item = selectedItem,
                tastes = Datasource.tastes,
                buttonTextId = R.string.add_to_cart,
                onDismissRequest = { showBottomSheet = false },
                onChipClicked = { chocolate: Chocolate, index: Int ->
                    selectedChocolates[index] = chocolate
                }, //todo
                onButtonClicked = {
                    if (selectedItem != null) {
//                        Log.d("chip9", selectedChocolates.first().toString())
                        if (selectedItem is ChocolateForm) (selectedItem as? ChocolateForm)?.updateChocolate(selectedChocolates.first())
                        if (selectedItem is ChocoSet) (selectedItem as? ChocoSet)?.updateChocolates(selectedChocolates)
                        viewModel.addItem(selectedItem!!)
                    }
                    showBottomSheet = false

                }, //todo add to cart and snackbar
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (showEditBottomSheet) {
            TasteBottomSheet(
                item = selectedEditItem,
                tastes = Datasource.tastes,
                buttonTextId = R.string.change,
                onDismissRequest = { showEditBottomSheet = false },
                onChipClicked = { chocolate, form ->
                    selectedEditTaste = chocolate
                }, //todo
                onButtonClicked = {
                    if (selectedEditItem != null) {
                        if (selectedChocoSet != null) {
                            selectedChocoSet?.updateSubItem(selectedEditItem!!, selectedEditTaste!!)
                        } else {
                            selectedEditItem?.updateChocolate(selectedEditTaste)
                            viewModel.setChocolate(selectedEditItem!!, selectedEditTaste!!)
                        }
                        showEditBottomSheet = false
                    }
                }, //todo add to cart and snackbar

//                selectedChocolates = selectedChocolates,

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

fun selectItem(item: Orderable) {

}


/*items = listOf(
                        Datasource.chocoSets[0],
                        ChocolateForm(chocolate = Datasource.tastes[0], form = Datasource.forms[2]),
                        ChocolateForm(chocolate = Datasource.tastes[0], form = Datasource.forms[1])
                    )*/