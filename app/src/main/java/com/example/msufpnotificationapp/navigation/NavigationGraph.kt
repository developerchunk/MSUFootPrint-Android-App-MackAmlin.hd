package com.example.msufpnotificationapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.msufpnotificationapp.SharedViewModel
import com.example.msufpnotificationapp.screens.AddNotificationScreen
import com.example.msufpnotificationapp.screens.MainScreen
import com.example.msufpnotificationapp.screens.NotificationDetails
import com.example.msufpnotificationapp.screens.SplashScreen

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