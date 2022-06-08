package uk.co.jordanterry.battleblips.game


sealed interface Cell {
    data class Label(
        val text: String,
    ) : Cell
    object BlankCell : Cell
    data class SelectedCell(
        val index: Int,
        val selected: Boolean,
        val unselect: SelectedCell.() -> Unit,
        val select: SelectedCell.() -> Unit,
    ) : Cell
    object BlipCell : Cell
    object HitCell : Cell
}