package com.example.msufpnotificationapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NotificationModel::class],
    version = 1,
    exportSchema = false,
)
abstract class Database: RoomDatabase() {

    abstract fun notificationDatabase(): NotificationDao

}