package uk.co.jordanterry.battleblips.game.board

import uk.co.jordanterry.battleblips.viewmodels.AbsViewModel

class BattleBlipsViewModel : AbsViewModel<BattleBlipsViewModel.UiModel>(
    default = UiModel.Loading
) {
    private val keys = "abcdefghijklmnopqrstuvwxyz"
    init {
        val width = 7
        val height = 7
        _uiModel.postValue(
            UiModel.Loaded(
                player = Player("You", createLabeledGrid(width, height)),
                opponent = Player("Opponent", createGrid(width, height))
            )
        )
    }

    private fun createGrid(width: Int, height: Int): Grid {
        return Grid(
            width = width,
            height = height,
            cells = mutableListOf<Cell>().apply {
                for (y in (0 until height)) {
                    for (x in (0 until width)) {
                        add(Cell.EmptyCell)
                    }
                }
            }
        )
    }

    private fun createLabeledGrid(width: Int, height: Int): Grid {
        val labeledWidth = width + 1
        val labeledHeight = height + 1
        return Grid(
            width = labeledWidth,
            height = labeledHeight,
            cells = mutableListOf<Cell>().apply {
                for (y in (0 until labeledHeight)) {
                    for (x in (0 until labeledWidth)) {
                        when {
                            x == 0 && y == 0 -> Cell.BlankCell
                            y % labeledHeight == 0 -> Cell.Label(keys[x - 1].toString())
                            x % labeledWidth == 0 -> Cell.Label(y.toString())
                            else -> Cell.EmptyCell
                        }.also(::add)
                    }
                }
            }
        )
    }

    sealed class UiModel {
        object Loading : UiModel()
        data class Loaded(
            val player: Player,
            val opponent: Player
        ) : UiModel()
    }
    data class Grid(
        val width: Int,
        val height: Int,
        val cells: List<Cell>
    )
    data class Player(
        val name: String,
        val grid: Grid
    )

    sealed interface Cell {
        data class Label(
            val text: String,
        ) : Cell
        object BlankCell : Cell
        object EmptyCell : Cell
        object BlipCell : Cell
        object HitCell : Cell
    }
}
