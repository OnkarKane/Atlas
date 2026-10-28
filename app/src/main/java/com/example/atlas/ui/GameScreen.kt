package com.example.atlas.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.atlas.data.GamePlayer
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen() {
    // Whose turn it is
    var currentPlayerIndex by remember { mutableIntStateOf(0) }

// The last place said
    var lastPlace by remember { mutableStateOf("") }

// Current letter players must match
    var currentLetter by remember { mutableStateOf('A') }

// The player's input
    var inputText by remember { mutableStateOf("") }

// Timer
    var timeLeft by remember { mutableIntStateOf(60) }

// All places said so far (for strike checking, invisible to players)
    val usedPlaces = remember { mutableStateListOf<String>() }

// Players and their strike counts
    val players = remember { mutableStateListOf<GamePlayer>() }


    //TIMER LOGIC
    LaunchedEffect(key1 = timeLeft){
        if (timeLeft>0){
            delay(1000L)
            timeLeft--
        } else{
            players[currentPlayerIndex].strikes++
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Timer") })
        }
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            // This part should contain the Giant Letter, followed by the last letter of lastPlace
            Text(text = "Current Letter: $currentLetter")
            Text(text = "Last Place: $lastPlace")

            Spacer(modifier = Modifier.height(16.dp))

            //Player List with their strikes shown ?
        }
    }
}
