package com.example.chocolateapp

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chocolateapp.ui.ChocolateTastesScreen
import com.example.chocolateapp.ui.FormsScreen
import com.example.chocolateapp.ui.OrderScreen
import com.example.chocolateapp.ui.OrderViewModel
import com.example.chocolateapp.ui.SetScreen


sealed class ChocolateScreen (
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val iconId: Int
) {
    object Taste: ChocolateScreen("taste", R.string.chocolate, R.drawable.ic_chocolate)
    object Set: ChocolateScreen("sets",R.string.sets, R.drawable.ic_set)
    object Forms: ChocolateScreen("forms",R.string.forms, R.drawable.ic_statue)
    object Order: ChocolateScreen("order",R.string.order, R.drawable.ic_cart)
}

val screens = listOf(
    ChocolateScreen.Taste,
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
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
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
@Composable
fun ChocolateApp (
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
    ) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = currentDestination?.route
    Scaffold (
        bottomBar = {
            ChocolateBottomNavBar(
                items = screens,
                navController = navController,
            )
        },
        modifier = Modifier.systemBarsPadding()
    ){ paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ChocolateScreen.Taste.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = ChocolateScreen.Taste.route) {
                ChocolateTastesScreen(title = stringResource(id = ChocolateScreen.Taste.resourceId))
            }
            composable(route = ChocolateScreen.Set.route) {
                SetScreen(title = stringResource(id = ChocolateScreen.Set.resourceId))
            }
            composable(route = ChocolateScreen.Forms.route) {
                FormsScreen(title = stringResource(id = ChocolateScreen.Forms.resourceId))
            }
            composable(route = ChocolateScreen.Order.route) {
                OrderScreen(title = stringResource(id = ChocolateScreen.Order.resourceId))
            }
        }
    }
}

@Preview
@Composable
fun ChocolateAppPreview() {
    ChocolateApp()
}