package uk.co.jordanterry.battleblips.game

data class Grid(
    val width: Int,
    val height: Int,
    val cells: List<Cell>
)