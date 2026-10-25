package com.example.atlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.atlas.ui.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.atlas.ui.Routes
import com.example.atlas.ui.CreateRoomScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Routes.HOME){
                composable(Routes.HOME){
                    HomeScreen(navController = navController)
                }
                composable(Routes.CREATE_ROOM){backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")?:""
                    CreateRoomScreen(navController = navController, username = username)
                }
            }
        }
    }
}
