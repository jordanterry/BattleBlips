package uk.co.jordanterry.battleblips.game.board

import uk.co.jordanterry.battleblips.game.Grid
import uk.co.jordanterry.battleblips.game.GridFactory
import uk.co.jordanterry.battleblips.knit.KnitState
import uk.co.jordanterry.battleblips.knit.KnitViewModel

class BattleBlipsViewModel : KnitViewModel<BattleBlipsViewModel.UiModel>(
    default = UiModel.Loading
) {
    private val gridFactory = GridFactory()
    private val width: Int = 7
    private val height: Int = 7

    init {
        setState<UiModel.Loading> {
            UiModel.Loaded(
                player = Player.Ready(
                    name = "You",
                    grid = createGrid(0),
                    selected = Selected.None
                ),
                opponent = Player.Waiting("Opponent")
            )
        }
    }

    private fun selectCell(index: Int) {
        setState<UiModel.Loaded>  {
            val player = this.player as Player.Ready
            copy(
                player = player.copy(
                        grid = createGrid(index),
                        selected = Selected.Target(index)
                    )
            )
        }
    }

    private fun unselectCell() {
        setState<UiModel.Loaded> {
            copy(
                player = (player as Player.Ready).copy(
                    grid = gridFactory.createLabeledGrid(width, height,0, ::selectCell, ::unselectCell),
                    selected = Selected.None
                )
            )
        }
    }

    private fun createGrid(index: Int): Grid {
        return gridFactory.createLabeledGrid(
            width,
            height,
            index,
            ::selectCell,
            ::unselectCell
        )
    }
    sealed interface UiModel : KnitState {
        object Loading : UiModel
        data class Loaded(
            val player: Player,
            val opponent: Player
        ) : UiModel
    }

    sealed interface Player {
        val name: String
        data class Waiting(
            override val name: String
        ) : Player
        data class SetUp(
            override val name: String
        ) : Player
        data class Ready(
            override val name: String,
            val grid: Grid,
            val selected: Selected = Selected.None
        ) : Player
    }
    sealed interface Selected {
        object None : Selected
        data class Target(
            val index: Int
        ) : Selected
    }
}
