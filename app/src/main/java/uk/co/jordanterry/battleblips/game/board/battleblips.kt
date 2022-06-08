package uk.co.jordanterry.battleblips.game.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.UiMode
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
                        icon = { Icon(screen.icon, contentDescription = null) },
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
        val uiModel by battleBlipsViewModel.states.collectAsState(battleBlipsViewModel.state)
        when (uiModel) {
            is BattleBlipsViewModel.UiModel.Loaded -> BattleBlipsLoaded(navHostController, uiModel as BattleBlipsViewModel.UiModel.Loaded)
            is BattleBlipsViewModel.UiModel.Loading -> BattleBlipsLoading()
        }
    }
}

sealed class Screen(val route: String,  val title: String, val icon: ImageVector) {
    object Player : Screen("player", "Player", Icons.Filled.Face)
    object Opponent : Screen("opponent", "Opponent", Icons.Filled.Phone)
}

@Composable
fun PlayerScreen(player: BattleBlipsViewModel.Player) {
    when (player) {
        is BattleBlipsViewModel.Player.Ready -> BattleBlipsPlayerGrid(player = player)
        is BattleBlipsViewModel.Player.SetUp -> BattleBlipsPlayerSetUp()
        is BattleBlipsViewModel.Player.Waiting -> BattleBlipsPlayerWaiting(player = player)
    }
}

@Composable
fun BattleBlipsPlayerSetUp() {

}

@Composable
fun BattleBlipsPlayerWaiting(player: BattleBlipsViewModel.Player.Waiting) {
    Text(text = "Waiting for ${player.name}")
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
