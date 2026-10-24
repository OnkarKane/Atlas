package com.example.atlas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoom(navController: NavController) {
    val playerNumbers = listOf(2, 3, 4, 5, 6, 7, 8)
    var numberOfPlayers by remember { mutableIntStateOf(playerNumbers.first()) }
    var expanded by remember { mutableStateOf(false) }

    val timeLimits = listOf(15, 30, 45, 60)
    var timeLimit by remember { mutableIntStateOf(timeLimits.first()) }

    var codeDialog by remember { mutableStateOf(false) }
    var roomCode by remember { mutableIntStateOf(0) }

    // Clipboard manager to handle copying the code
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Create Room",
            style = MaterialTheme.typography.headlineMedium
        )

        // Dropdown: Number of Players
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = numberOfPlayers.toString(),
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Number of Players") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                playerNumbers.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.toString()) },
                        onClick = {
                            numberOfPlayers = item
                            expanded = false
                        }
                    )
                }
            }
        }

        // Radio Buttons: Time Limit
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Time Limit (seconds)",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                timeLimits.forEach { item ->
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .selectable(
                                selected = timeLimit == item,
                                onClick = { timeLimit = item },
                                role = Role.RadioButton
                            )
                            .padding(vertical =.8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = (timeLimit == item),
                            onClick = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "$item s", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        // Action Button
        Button(
            onClick = {
                // Generate a random 6-digit room code (or change logic as needed)
                roomCode = Random.nextInt(100000, 999999)
                codeDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Generate Room")
        }

        // Room Code Dialog
        if (codeDialog) {
            AlertDialog(
                onDismissRequest = { codeDialog = false },
                title = { Text(text = "Room Code Created Successfully") },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Your Room Code:",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // Big bold letter display for the room code
                        Text(
                            text = roomCode.toString(),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 4.sp
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        // Copy Button with Icon
                        OutlinedButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(roomCode.toString()))
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy Icon"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Copy Code")
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            codeDialog = false
                            // TODO: Add navigation to the actual game room screen here if needed
                            // navController.navigate("game_room/$roomCode")
                        }
                    ) {
                        Text(text = "Enter Room")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { codeDialog = false }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
    }
}