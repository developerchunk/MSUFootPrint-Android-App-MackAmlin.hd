package com.example.msufpnotificationapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.msufpnotificationapp.SharedViewModel
import com.example.msufpnotificationapp.database.NotificationModel
import com.example.msufpnotificationapp.navigation.NavRoute
import com.example.msufpnotificationapp.ui.theme.*
import com.example.msufpnotificationapp.util.Constants
import com.example.msufpnotificationapp.util.addZeroToStart
import com.example.msufpnotificationapp.util.components.DisplayAlertDialog
import com.example.msufpnotificationapp.util.components.NotificationHistoryItem
import com.example.msufpnotificationapp.util.components.TopAppBarHistory
import com.example.msufpnotificationapp.util.state.NotificationStatus
import com.example.msufpnotificationapp.util.state.RequestState
import com.example.msufpnotificationapp.util.state.SearchBarState
import java.util.*

@Composable
fun MainScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    val notificationAction = sharedViewModel.notificationAction
    sharedViewModel.notificationAction(action = notificationAction.value)

    val allNotifications = sharedViewModel.allNotifications.collectAsState()
    val searchAllNotifications by sharedViewModel.searchAllNotifications.collectAsState()

    val calender = Calendar.getInstance()

    val year = calender.get(Calendar.YEAR)
    val month = (calender.get(Calendar.MONTH) + 1).addZeroToStart()
    val day = calender.get(Calendar.DAY_OF_MONTH).addZeroToStart()

    val hour = calender.get(Calendar.HOUR_OF_DAY).addZeroToStart()
    val minute = calender.get(Calendar.MINUTE).addZeroToStart()
    val second = calender.get(Calendar.SECOND).addZeroToStart()

    val time = "$year:$month:$day:$hour:$minute:$second"

    val scrollState = rememberScrollState()

    val searchBarState: SearchBarState by sharedViewModel.searchBarState
    val searchBarText: String by sharedViewModel.searchBarText

    var menuClicked by remember {
        mutableStateOf(false)
    }

    var menuSelected by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBarHistory(
                sharedViewModel = sharedViewModel,
                searchBarState = searchBarState,
                searchBarText = searchBarText,
                onMenuCLicked = { menu ->
                    menuSelected = menu
                    menuClicked = true
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onCLick = {
                    navController.navigate(route = NavRoute.AddNotificationScreenRoute.route)
                }
            )
        },
        backgroundColor = Dark
    ) {
        Column(
            Modifier
                .padding(it)
                .verticalScroll(scrollState)
        ) {

            if (menuClicked) {
                when (menuSelected) {
                    Constants.MARK_ALL_SEEN -> {
                        DisplayAlertDialog(
                            title = "Sure to mark all seen",
                            message = "You are just one step away form marking all your notifications to SEEN",
                            openDialog = true,
                            onCloseClicked = {
                                menuClicked = false
                            },
                            onYesClicked = {
                                sharedViewModel.markAllSeen(time = time)
                                menuClicked = false
                            }
                        )
                    }
                    Constants.MARK_ALL_UNSEEN -> {
                        DisplayAlertDialog(
                            title = "Sure to mark all unseen",
                            message = "You are just one step away form marking all your notifications to UNSEEN",
                            openDialog = true,
                            onCloseClicked = {
                                menuClicked = false
                            },
                            onYesClicked = {
                                sharedViewModel.markAllUnseen()
                                menuClicked = false
                            }
                        )

                    }
                    Constants.DELETE_ALL -> {
                        DisplayAlertDialog(
                            title = "Sure to Delete all notifications",
                            message = "You are just one step away form deleting all your notifications",
                            openDialog = true,
                            onCloseClicked = {
                                menuClicked = false
                            },
                            onYesClicked = {
                                sharedViewModel.deleteAllNotifications()
                                menuClicked = false
                            },
                            captchaVerification = true
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Color.Transparent)
            )

            NotificationHistory(
                allNotifications = allNotifications.value,
                searchAllNotifications = searchAllNotifications,
                searchBarState = searchBarState,
                sharedViewModel = sharedViewModel,
                onClicked = { notificationModel ->

                    sharedViewModel.notificationModel.value = notificationModel

                    if (notificationModel.status == NotificationStatus.UNSEEN.name) {
                        sharedViewModel.updateNotification(
                            NotificationModel(
                                id = notificationModel.id,
                                name = notificationModel.name,
                                message = notificationModel.message,
                                status = NotificationStatus.SEEN.name,
                                timeSeen = time
                            )
                        )
                    }
                    navController.navigate(route = NavRoute.NotificationDetailsRoute.route)
                }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Transparent)
            )

        }
    }

}

@Composable
fun NotificationHistory(
    allNotifications: RequestState<List<NotificationModel>>,
    searchAllNotifications: RequestState<List<NotificationModel>>,
    searchBarState: SearchBarState,
    sharedViewModel: SharedViewModel,
    onClicked: (NotificationModel) -> Unit
) {

    if (searchBarState == SearchBarState.TRIGGERED) {
        if (searchAllNotifications is RequestState.Success) {
            HistoryNotificationContent(
                allNotifications = searchAllNotifications.data,
                sharedViewModel = sharedViewModel,
                onClicked = { notificationModel ->
                    onClicked(notificationModel)
                }
            )
        }
    } else {
        if (allNotifications is RequestState.Success) {
            HistoryNotificationContent(
                allNotifications = allNotifications.data,
                sharedViewModel = sharedViewModel,
                onClicked = { notificationModel ->
                    onClicked(notificationModel)
                }
            )
        }
    }

}

@Composable
fun HistoryNotificationContent(
    allNotifications: List<NotificationModel>,
    onClicked: (NotificationModel) -> Unit,
    sharedViewModel: SharedViewModel
) {

    sharedViewModel.allNotificationsModels = allNotifications.toMutableList()

    allNotifications.forEach {
        NotificationHistoryItem(
            notificationModel = it,
            onCLicked = { notificationModel ->
                onClicked(notificationModel)
            }
        )
    }

}

@Composable
fun FloatingActionButton(
    onCLick: () -> Unit
) {

    val buttonColor = Brush.horizontalGradient(colors = listOf(Yellow, Pink, UIRed))

    Surface(
        modifier = Modifier
            .padding(bottom = 15.dp, end = 15.dp)
            .width(120.dp)
            .height(55.dp)
            .clickable {
                onCLick()
            },
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = buttonColor),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                tint = UIWhite
            )

            Text(
                text = "Add",
                fontSize = LARGE_TEXT_SIZE,
                color = UIWhite,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium
            )

        }

    }

}