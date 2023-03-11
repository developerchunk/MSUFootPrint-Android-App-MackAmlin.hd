package com.developerstring.noteapp

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerstring.noteapp.database.NotificationModel
import com.developerstring.noteapp.database.NotificationRepository
import com.developerstring.noteapp.datastore.OnBoardingDatabase
import com.developerstring.noteapp.util.state.NotificationAction
import com.developerstring.noteapp.util.state.NotificationStatus
import com.developerstring.noteapp.util.state.RequestState
import com.developerstring.noteapp.util.state.SearchBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {

    var allNotificationsModels: MutableList<NotificationModel> = mutableListOf()

    private var _allNotifications =
        MutableStateFlow<RequestState<List<NotificationModel>>>(RequestState.Idle)

    val allNotifications: StateFlow<RequestState<List<NotificationModel>>> = _allNotifications


    val notificationModel: MutableState<NotificationModel> = mutableStateOf(NotificationModel())

    fun getAllNotifications() {
        _allNotifications.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllNotifications.collect {
                    _allNotifications.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allNotifications.value = RequestState.Error(e)
        }
    }

    fun addNotification(
        notificationModel: NotificationModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNotifications(notificationModel = notificationModel)
        }
    }

    fun updateNotification(
        notificationModel: NotificationModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotifications(notificationModel = notificationModel)
        }
    }

    // delete Notification

    val notificationAction: MutableState<NotificationAction> =
        mutableStateOf(NotificationAction.NO_ACTION)

    private fun deleteNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotifications(notificationModel = notificationModel.value)
        }
        this.notificationAction.value = NotificationAction.NO_ACTION
    }

    fun notificationAction(
        action: NotificationAction,
    ) {
        when (action) {
            NotificationAction.DELETE -> deleteNotification()
            else -> {

            }
        }
        this.notificationAction.value = NotificationAction.NO_ACTION
    }

    // search
    private var _searchAllNotifications =
        MutableStateFlow<RequestState<List<NotificationModel>>>(RequestState.Idle)
    val searchAllNotifications: StateFlow<RequestState<List<NotificationModel>>> =
        _searchAllNotifications

    val searchBarState: MutableState<SearchBarState> = mutableStateOf(SearchBarState.DELETE)

    val searchBarText: MutableState<String> = mutableStateOf("")

    fun getSearchedNotification(searchQuery: String) {
        _searchAllNotifications.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchAllNotifications(searchQuery = searchQuery).collect {
                    _searchAllNotifications.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _searchAllNotifications.value = RequestState.Error(e)
        }

        searchBarState.value = SearchBarState.TRIGGERED
    }

    fun markAllSeen(
        time: String,
    ) {

        val newList = mutableListOf<NotificationModel>()

        allNotificationsModels.forEachIndexed { index, notificationModel ->
            if (notificationModel.status == NotificationStatus.UNSEEN.name) {
                newList.add(
                    index = index, element = NotificationModel(
                        id = notificationModel.id,
                        name = notificationModel.name,
                        message = notificationModel.message,
                        status = NotificationStatus.SEEN.name,
                        timeSeen = time
                    )
                )
            } else {
                newList.add(index = index, element = notificationModel)
            }

            updateNotification(newList[index])

        }

    }

    fun markAllUnseen() {

        val newList = mutableListOf<NotificationModel>()

        allNotificationsModels.forEachIndexed { index, notificationModel ->
            if (notificationModel.status == NotificationStatus.SEEN.name) {
                newList.add(
                    index = index, element = NotificationModel(
                        id = notificationModel.id,
                        name = notificationModel.name,
                        message = notificationModel.message,
                        status = NotificationStatus.UNSEEN.name,
                        timeSeen = ""
                    )
                )
            } else {
                newList.add(index = index, element = notificationModel)
            }

            updateNotification(newList[index])

        }

    }

    fun deleteAllNotifications() {
        viewModelScope.launch {
            repository.deleteAllNotifications()
        }
    }


    fun saveOnBoardingStatus(context: Context) {
        viewModelScope.launch {
            OnBoardingDatabase(context).saveOnBoardingStatus("YES")
        }
    }

    private val _onBoardingStatus = MutableStateFlow("")
    val onBoardingStatus: StateFlow<String> = _onBoardingStatus
    fun getOnBoardingStatus(context: Context) {
        viewModelScope.launch {
            OnBoardingDatabase(context).getOnBoardingStatus.collect {
                _onBoardingStatus.value = it!!
            }
        }
    }

}