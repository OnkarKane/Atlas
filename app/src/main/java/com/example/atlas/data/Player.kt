package com.example.atlas.data

data class GamePlayer(
    val name: String,
    var strikes: Int = 0,
    var isEliminated: Boolean = false
)
