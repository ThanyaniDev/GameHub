package com.thanyani.gamehub.data

import com.thanyani.gamehub.model.Game
import retrofit2.http.GET

interface GameService {
    @GET("games?platform=pc")
    suspend fun getGames(): List<Game>
}
