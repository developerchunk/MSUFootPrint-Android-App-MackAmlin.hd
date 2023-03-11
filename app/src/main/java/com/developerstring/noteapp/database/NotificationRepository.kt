package com.developerstring.noteapp.database

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class NotificationRepository @Inject constructor(private val notificationDao: NotificationDao) {

    val getAllNotifications: Flow<List<NotificationModel>> = notificationDao.getAllNotifications()

    suspend fun addNotifications(notificationModel: NotificationModel) {
        notificationDao.addNotifications(notificationModel = notificationModel)
    }

    suspend fun updateNotifications(notificationModel: NotificationModel) {
        notificationDao.updateNotifications(notificationModel = notificationModel)
    }

    suspend fun deleteNotifications(notificationModel: NotificationModel) {
        notificationDao.deleteNotifications(notificationModel = notificationModel)
    }

    fun searchAllNotifications(searchQuery: String): Flow<List<NotificationModel>> {
        return notificationDao.searchAllNotifications(searchQuery = searchQuery)
    }

    suspend fun deleteAllNotifications() {
        notificationDao.deleteAllNotifications()
    }

}
