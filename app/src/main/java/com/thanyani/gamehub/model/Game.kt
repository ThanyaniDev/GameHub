package com.thanyani.gamehub.model

data class Game(
    val title: String,
    val thumbnail: String,
    val gameUrl: String,
    val platform: String,
    val publisher: String,
    val genre: String,
    val developer: String,
    val releaseDate: String,
    val freetogameProfileUrl: String
)
