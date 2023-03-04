package com.example.msufpnotificationapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notification_table ORDER BY id DESC")
    fun getAllNotifications(): Flow<List<NotificationModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotifications(notificationModel: NotificationModel)

    @Update
    suspend fun updateNotifications(notificationModel: NotificationModel)

    @Delete
    suspend fun deleteNotifications(notificationModel: NotificationModel)

    @Query("SELECT * FROM notification_table WHERE (name LIKE :searchQuery OR message LIKE :searchQuery OR timeSeen LIKE :searchQuery OR status LIKE :searchQuery) ORDER BY id DESC")
    fun searchAllNotifications(searchQuery: String): Flow<List<NotificationModel>>

    @Query("DELETE FROM notification_table")
    suspend fun deleteAllNotifications()

}