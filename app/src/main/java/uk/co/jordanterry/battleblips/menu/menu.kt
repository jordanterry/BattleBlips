package uk.co.jordanterry.battleblips.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
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
            when (val uiModel = menuViewModel.uiModel.value!!) {
                is MenuViewModel.UiModel.Loading -> Text(text = "Loading!")
                is MenuViewModel.UiModel.Loaded -> LoadedMenu(
                    uiModel = uiModel,
                    navController = navController
                )
            }
        }
    )
}

@Composable
fun LoadedMenu(
    uiModel: MenuViewModel.UiModel.Loaded,
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

