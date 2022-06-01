package uk.co.jordanterry.battleblips.game.board

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BattleBlipsPlayerGrid(
    player: BattleBlipsViewModel.Player
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(player.grid.width),
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 8.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        items(player.grid.cells.size) { index ->
            val cell = player.grid.cells[index]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .aspectRatio(1f)
            ) {
                when (cell) {
                    is BattleBlipsViewModel.Cell.BlankCell -> BlankCell()
                    is BattleBlipsViewModel.Cell.EmptyCell -> UnselectedCell(cell = cell)
                    is BattleBlipsViewModel.Cell.Label -> LabelCell(cell = cell)
                    BattleBlipsViewModel.Cell.BlipCell -> BlankCell()
                    BattleBlipsViewModel.Cell.HitCell -> BlankCell()
                }
            }
        }
    }
}

@Composable
fun BlankCell() {

}

@Composable
fun UnselectedCell(cell: BattleBlipsViewModel.Cell.EmptyCell) {
    Box(
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxWidth()
            .aspectRatio(1f)
    )
}

@Composable
fun LabelCell(cell: BattleBlipsViewModel.Cell.Label) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(text = cell.text.uppercase(Locale.ROOT))
    }
}