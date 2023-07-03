package com.thanyani.gamehub.data

import com.thanyani.gamehub.model.Game
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.freetogame.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val gameService: GameService = retrofit.create(GameService::class.java)

    suspend fun fetchGames(): List<Game> {
        return gameService.getGames()
    }
}
