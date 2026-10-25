package com.example.atlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.example.atlas.ui.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.atlas.ui.Routes
import com.example.atlas.ui.CreateRoomScreen
import com.example.atlas.ui.PlayerLobby


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            //NavHost Important stuff
            NavHost(navController = navController, startDestination = Routes.HOME){
                composable(Routes.HOME){
                    HomeScreen(navController = navController)
                }
                composable(Routes.CREATE_ROOM, arguments = listOf(navArgument("username"){type =
                    NavType.StringType})){ backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")?:""
                    CreateRoomScreen(navController = navController, username = username)
                }
                composable(Routes.PLAYERLOBBY,
                    arguments = listOf(navArgument("roomCode"){type = NavType.StringType},
                        navArgument("username"){type = NavType.StringType},
                        navArgument("isHost") { type = NavType.BoolType }
                    )
                ){
                    backStackEntry ->
                    val roomCode = backStackEntry.arguments?.getString("roomcode")?:""
                    val username = backStackEntry.arguments?.getString("username")?:""
                    val isHost = backStackEntry.arguments?.getBoolean("isHost")?:false

                    PlayerLobby(
                        navController = navController,
                        roomCode = roomCode,
                        username = username,
                        isHost = isHost
                    )
                }
            }
        }
    }
}
