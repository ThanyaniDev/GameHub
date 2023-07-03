package com.thanyani.gamehub.ui.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thanyani.gamehub.model.Game
import com.thanyani.gamehub.ui.viewModel.GameViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.thanyani.gamehub.R


class MainActivity : ComponentActivity() {
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen(gameViewModel)
        }
    }
}

@Composable
fun GameScreen(
    gameViewModel: GameViewModel
) {
    val loading by gameViewModel.loading
    val error by gameViewModel.error
    val games by gameViewModel.games
    val gamesLoaded = games.isNotEmpty()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!gamesLoaded) {
                Button(
                    onClick = { gameViewModel.loadGames() },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Load Games")
                }
            }

            if (loading) {
                CircularProgressIndicator()
            } else if (error) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error occurred while loading games.")
                    Button(
                        onClick = { gameViewModel.loadGames() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Retry")
                    }
                }
            } else {
                Column {
                    LazyColumn {
                        items(games) { game ->
                            GameItem(game)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameItem(game: Game) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.padding(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            val painter = rememberImagePainter(
                data = game.thumbnail,
                builder = {
                    placeholder(R.drawable.placeholder_image)
                    error(R.drawable.error_image)
                }
            )

            Image(
                painter = painter,
                contentDescription = game.title,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = game.title ?: "", style = MaterialTheme.typography.subtitle2)
                Text(text = game.releaseDate ?: "", style = MaterialTheme.typography.caption)
                Text(text = game.developer ?: "", style = MaterialTheme.typography.caption)
                Text(text = game.genre ?: "", style = MaterialTheme.typography.caption)
                Text(text = game.publisher ?: "", style = MaterialTheme.typography.caption)
            }
        }
    }
}



