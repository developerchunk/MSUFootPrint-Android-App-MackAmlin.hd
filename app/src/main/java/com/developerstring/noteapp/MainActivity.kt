package com.developerstring.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.developerstring.noteapp.navigation.NavigationGraph
import com.developerstring.noteapp.ui.theme.ExtraDark
import com.developerstring.noteapp.ui.theme.MSUFPNotificationAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MSUFPNotificationAppTheme {
                // A surface container using the 'background' color from the theme

                sharedViewModel.getAllNotifications()

                navController = rememberNavController()

                NavigationGraph(navController = navController, sharedViewModel = sharedViewModel)

                val systemController = rememberSystemUiController()
                systemController.setSystemBarsColor(
                    color = ExtraDark
                )

            }
        }
    }
}
