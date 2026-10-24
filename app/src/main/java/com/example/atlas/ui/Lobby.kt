package com.example.atlas.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun createRoom(){
    var numberOfPlayers by remember { mutableIntStateOf(0) }
    var timeLimit by remember { mutableIntStateOf(0) }

}