package com.developerstring.noteapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.developerstring.noteapp.SharedViewModel
import com.developerstring.noteapp.screens.AddNotificationScreen
import com.developerstring.noteapp.screens.MainScreen
import com.developerstring.noteapp.screens.NotificationDetails
import com.developerstring.noteapp.screens.SplashScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.SplashScreenRoute.route
    ) {

        composable(route = NavRoute.SplashScreenRoute.route) {
            SplashScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        composable(route = NavRoute.MainScreenRoute.route) {
            MainScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        composable(route = NavRoute.AddNotificationScreenRoute.route) {
            AddNotificationScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        composable(route = NavRoute.NotificationDetailsRoute.route) {
            NotificationDetails(navController = navController, sharedViewModel = sharedViewModel)
        }

    }

}