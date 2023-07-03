package com.thanyani.gamehub.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanyani.gamehub.data.GameRepository
import com.thanyani.gamehub.model.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val gameRepository: GameRepository = GameRepository()
    private val _games = mutableStateOf<List<Game>>(emptyList())
    val games: State<List<Game>> = _games

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _error = mutableStateOf(false)
    val error: State<Boolean> = _error

    private val _navigateToGameScreen = MutableSharedFlow<Unit>()
    val navigateToGameScreen: Flow<Unit> = _navigateToGameScreen.asSharedFlow()

    fun loadGames() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = false
                _games.value = gameRepository.fetchGames()
                _navigateToGameScreen.emit(Unit)
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }
}




