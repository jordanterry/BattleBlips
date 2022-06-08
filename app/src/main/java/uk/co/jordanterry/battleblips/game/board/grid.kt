package uk.co.jordanterry.battleblips.game.board

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import uk.co.jordanterry.battleblips.game.Cell
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BattleBlipsPlayerGrid(
    player: BattleBlipsViewModel.Player.Ready
) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
                        is Cell.BlankCell -> BlankCell()
                        is Cell.Label -> LabelCell(cell = cell)
                        is Cell.BlipCell -> BlankCell()
                        is Cell.HitCell -> BlankCell()
                        is Cell.SelectedCell -> GameCell(cell = cell)
                    }
                }
            }
        }
    }
}

@Composable
fun GameCell(
    cell: Cell.SelectedCell,
    isSelected: MutableState<Boolean> = mutableStateOf(false),
    modifier: Modifier = Modifier
) {
    val colour by animateColorAsState(
        if (isSelected.value) Color.Red else Color.LightGray
    )
    Box(
        modifier = modifier
            .background(
                color = colour
            )
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable {
                if(cell.selected) {
                    cell.unselect(cell)
                } else {
                    cell.select(cell)
                }
            }
    )
}

@Composable
fun BlankCell() = Unit

@Composable
fun LabelCell(cell: Cell.Label) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(text = cell.text.uppercase(Locale.ROOT))
    }
}