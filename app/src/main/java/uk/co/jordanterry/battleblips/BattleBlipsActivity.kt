package uk.co.jordanterry.battleblips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.co.jordanterry.battleblips.game.board.BattleBlipsScreen
import uk.co.jordanterry.battleblips.menu.MenuScreen
import uk.co.jordanterry.battleblips.ui.theme.BattleBlipsTheme

class BattleBlipsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            BattleBlipsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    NavHost(navController = navController, startDestination = "menu") {
                        composable("menu") {
                            MenuScreen(
                                navController = navController
                            )
                        }
                        composable("battleBlips") {
                            BattleBlipsScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

