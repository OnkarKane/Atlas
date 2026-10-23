package com.example.atlas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(){
    var usernameInput by remember { mutableStateOf("") }
    var hasAttemptedSubmit by remember { mutableStateOf(false) }
    val isUsernameValid = usernameInput.isNotEmpty() && usernameInput.length in 2..15
    Column (
        modifier = Modifier.fillMaxSize().padding(16 .dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Title
        Text(text = "Atlas",
            style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(16.dp))

        //Username
        Text(text = "Username")
        OutlinedTextField(value = usernameInput,
            onValueChange = {usernameInput= it ; hasAttemptedSubmit = false},
            label = {Text(text = "Enter your Username")},
            singleLine = true,

        )
        if (hasAttemptedSubmit && !isUsernameValid){
            Text(text = "Username should be between 2 and 15 characters",
                color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Buttons
        Button(onClick = { hasAttemptedSubmit = true }, modifier = Modifier.padding(top = 16 .dp)) {
            Text(text = "Create Room")
        }
        Button(onClick = { hasAttemptedSubmit = true }, modifier = Modifier.padding(top = 16 .dp)) {
            Text(text = "Join Room")

        }
    }
}