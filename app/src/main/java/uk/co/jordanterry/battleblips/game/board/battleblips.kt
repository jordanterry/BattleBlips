package uk.co.jordanterry.battleblips.game.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BattleBlipsScreen(
    battleBlipsViewModel: BattleBlipsViewModel = viewModel(),
    navController: NavController,
) {
    val items = listOf(
        Screen.Player,
        Screen.Opponent,
    )
    val navHostController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Battle blips!")
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navHostController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        when (val uiModel = battleBlipsViewModel.uiModel.value!!) {
            is BattleBlipsViewModel.UiModel.Loaded -> BattleBlipsLoaded(navHostController, uiModel)
            is BattleBlipsViewModel.UiModel.Loading -> BattleBlipsLoading()
        }

    }
}

sealed class Screen(val route: String,  val title: String) {
    object Player : Screen("player", "Player")
    object Opponent : Screen("opponent", "Opponent")
}

@Composable
fun PlayerScreen(player: BattleBlipsViewModel.Player) {
    BattleBlipsPlayerGrid(player = player)
}

@Composable
fun BattleBlipsLoading() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Loading!")
    }
}

@Composable
fun BattleBlipsLoaded(navHostController: NavHostController, uiModel: BattleBlipsViewModel.UiModel.Loaded) {
    NavHost(navController = navHostController, startDestination = Screen.Player.route) {
        composable(Screen.Player.route) { PlayerScreen(uiModel.player) }
        composable(Screen.Opponent.route) { PlayerScreen(uiModel.opponent) }
    }
}
