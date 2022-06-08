package uk.co.jordanterry.battleblips.menu

import uk.co.jordanterry.battleblips.knit.KnitViewModel

class MenuViewModel : KnitViewModel<MenuState>(
    default = MenuState.Loading
) {
    init {
        setState<MenuState.Loading> {
            MenuState.Loaded(
                menuOptions = listOf(
                    MenuOption("Single Player", "battleBlips", ::onSinglePlayer),
                    MenuOption("Multi Player", "battleBlips", ::onMultiplayer)
                ),
            )
        }
    }

    private fun onSinglePlayer() {

    }

    private fun onMultiplayer() {

    }


}