package uk.co.jordanterry.battleblips.menu

import uk.co.jordanterry.battleblips.knit.KnitState

sealed interface MenuState : KnitState {
    object Loading : MenuState
    data class Loaded(
        val menuOptions: List<MenuOption>
    ) : MenuState
}

data class MenuOption(
    val name: String,
    val destination: String,
    val onClick: () -> Unit
)