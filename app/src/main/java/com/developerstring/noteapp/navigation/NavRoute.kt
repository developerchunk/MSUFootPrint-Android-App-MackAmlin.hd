package com.developerstring.noteapp.navigation

sealed class NavRoute(val route: String) {

    object MainScreenRoute: NavRoute(route = "main_screen")

    object SplashScreenRoute: NavRoute(route = "splash_screen")

    object AddNotificationScreenRoute: NavRoute(route = "add_notification_screen")

    object NotificationDetailsRoute: NavRoute(route = "notification_details_screen")

}
