package com.example.atlas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    var roomCodeInput by remember { mutableStateOf("") }
    var joinRoom by remember { mutableStateOf(false) }
    var hasAttemptedSubmit by remember { mutableStateOf(false) }
    val isUsernameValid = usernameInput.isNotEmpty() && usernameInput.length in 2..15

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
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
        Button(
            onClick = {
                hasAttemptedSubmit = true
                if (isUsernameValid) {
                    // Handle Room Creation Logic here
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Create Room")
        }

        Button(
            onClick = {
                hasAttemptedSubmit = true
                // Only open the dialog if the username passes validation first
                if (isUsernameValid) {
                    joinRoom = true
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Join Room")
        }

        // The Floating Dialog Box
        if (joinRoom){
            AlertDialog(onDismissRequest = { joinRoom = false ; roomCodeInput = "" },
                title = {
                    Text(text = "Join a Room")
                },
                text = {
                    Column {
                        Text(text = "Enter the room code shared by your friend:")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = roomCodeInput,
                            onValueChange = { roomCodeInput = it },
                            label = { Text(text = "Room Code") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Handle actual room joining logic with roomCodeInput here
                            joinRoom = false
                        },
                        enabled = roomCodeInput.isNotEmpty() // Disables button until they type something
                    ) {
                        Text("Join")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            joinRoom = false
                            roomCodeInput = ""
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
