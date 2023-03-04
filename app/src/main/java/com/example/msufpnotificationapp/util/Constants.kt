package com.example.msufpnotificationapp.util

import com.example.msufpnotificationapp.database.NotificationModel
import com.example.msufpnotificationapp.util.state.NotificationStatus

object Constants {

    const val DATABASE_NAME = "notification_database"
    const val NOTIFICATION_TABLE = "notification_table"

    const val MARK_ALL_SEEN = "Mark all Seen"
    const val MARK_ALL_UNSEEN = "Mark all Unseen"
    const val DELETE_ALL = "Delete All"

    val TopAppBarMenuList = listOf(
        MARK_ALL_SEEN,
        MARK_ALL_UNSEEN,
        DELETE_ALL
    )

    const val ON_BOARDING_STATUS = "onBoardingDB"
    const val ON_BOARDING_STATUS_KEY = "on_boarding_db"

    val notificationList = listOf(
        NotificationModel(
            name = "Rohit",
            message = "Team Member, Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit",
            status = NotificationStatus.UNSEEN.name
        ),
        NotificationModel(
            name = "Siddarth",
            message = "Team Member, Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit",
            status = NotificationStatus.UNSEEN.name
        ),
        NotificationModel(
            name = "Makrand",
            message = "Team Member, Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit",
            status = NotificationStatus.UNSEEN.name
        ),
        NotificationModel(
            name = "Aditya",
            message = "Team Member, sample message",
            status = NotificationStatus.UNSEEN.name
        )
    )

}