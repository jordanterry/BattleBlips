package uk.co.jordanterry.battleblips.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = viewModel(),
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Battle blips!")
                }
            )
        },
        content = {
            val uiState by menuViewModel.states.collectAsState(menuViewModel.state)
            Menu(uiState = uiState, navController = navController)
        }
    )
}

@Composable
fun Menu(uiState: MenuState, navController: NavController) {
    when (uiState) {
        is MenuState.Loading -> Text(text = "Loading!")
        is MenuState.Loaded -> LoadedMenu(
            uiModel = uiState,
            navController = navController
        )
    }
}

@Composable
fun LoadedMenu(
    uiModel: MenuState.Loaded,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiModel.menuOptions.forEach { menuOption ->
            Button(onClick = { navController.navigate(menuOption.destination) }) {
                Text(text = menuOption.name)
            }
        }
    }
}

