package uk.co.jordanterry.battleblips.menu

import uk.co.jordanterry.battleblips.viewmodels.AbsViewModel

class MenuViewModel : AbsViewModel<MenuViewModel.UiModel>(
    default = UiModel.Loading
) {
    init {
        _uiModel.value = UiModel.Loaded(
            menuOptions = listOf(
                MenuOption("Single Player", "battleBlips", ::onSinglePlayer),
                MenuOption("Multi Player", "battleBlips", ::onMultiplayer)
            ),
        )
    }

    private fun onSinglePlayer() {

    }

    private fun onMultiplayer() {

    }

    sealed interface UiModel {
        object Loading : UiModel
        data class Loaded(
            val menuOptions: List<MenuOption>
        ) : UiModel
    }

    data class MenuOption(
        val name: String,
        val destination: String,
        val onClick: () -> Unit
    )
}