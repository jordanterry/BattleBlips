package uk.co.jordanterry.battleblips.game


class GridFactory {

    private val keys = "abcdefghijklmnopqrstuvwxyz"

    fun createGrid(width: Int, height: Int, selectedIndex: Int,
                   onSelect: (Int) -> Unit,
                   onUnselect: () -> Unit): Grid {
        return Grid(
            width = width,
            height = height,
            cells = mutableListOf<Cell>().apply {
                var index = 0
                for (y in (0 until height)) {
                    for (x in (0 until width)) {
                        add(Cell.SelectedCell(
                                index = index,
                                selected = selectedIndex == index,
                                unselect = { onSelect(this.index) },
                                select = { onUnselect() }
                            )
                        )
                    }
                    index++
                }
            }
        )
    }

    fun createLabeledGrid(
        width: Int,
        height: Int,
        selectedIndex: Int,
        onSelect: (Int) -> Unit,
        onUnselect: () -> Unit
    ): Grid {
        val labeledWidth = width + 1
        val labeledHeight = height + 1
        return Grid(
            width = labeledWidth,
            height = labeledHeight,
            cells = mutableListOf<Cell>().apply {
                var index = 0
                for (y in (0 until labeledHeight)) {
                    for (x in (0 until labeledWidth)) {
                        when {
                            x == 0 && y == 0 -> Cell.BlankCell
                            y % labeledHeight == 0 -> Cell.Label(x.toString())
                            x % labeledWidth == 0 -> Cell.Label(keys[y - 1].toString())
                            else -> Cell.SelectedCell(
                                index = index,
                                selected = selectedIndex == index,
                                unselect = { onUnselect() },
                                select = { onSelect(this.index) }
                            )
                        }.also(::add)
                        index++
                    }
                }
            }
        )
    }
}