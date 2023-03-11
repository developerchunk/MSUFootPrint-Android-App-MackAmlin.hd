package com.developerstring.noteapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developerstring.noteapp.util.Constants.NOTIFICATION_TABLE

@Entity(tableName = NOTIFICATION_TABLE)
data class NotificationModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val message: String = "",
    val status: String = "",
    val timeSeen: String = ""
)
