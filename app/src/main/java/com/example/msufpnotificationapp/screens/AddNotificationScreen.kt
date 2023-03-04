package com.example.msufpnotificationapp.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.msufpnotificationapp.SharedViewModel
import com.example.msufpnotificationapp.database.NotificationModel
import com.example.msufpnotificationapp.ui.theme.*
import com.example.msufpnotificationapp.util.addZeroToStart
import com.example.msufpnotificationapp.util.convertStringToAlphabets
import com.example.msufpnotificationapp.util.state.NotificationStatus
import com.example.msufpnotificationapp.util.timeStringToDate
import java.util.*

@Composable
fun AddNotificationScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    AddNotificationScreenContent(
        navController = navController,
        notificationModel = NotificationModel(),
        sharedViewModel = sharedViewModel,
        onSaveClicked = {
            navController.popBackStack()
            sharedViewModel.addNotification(
                NotificationModel(
                    name = it.name,
                    message = it.message,
                    status = NotificationStatus.UNSEEN.name
                )
            )
        },
        onDeleteClicked = {

        }
    )


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddNotificationScreenContent(
    navController: NavController,
    notificationModel: NotificationModel,
    sharedViewModel: SharedViewModel,
    onSaveClicked: (NotificationModel) -> Unit,
    onDeleteClicked: (NotificationModel) -> Unit
) {

    var name by rememberSaveable {
        mutableStateOf(notificationModel.name)
    }

    var message by rememberSaveable {
        mutableStateOf(notificationModel.message)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val buttonColor = Brush.horizontalGradient(colors = listOf(Yellow, Pink, UIRed))

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val scrollState = rememberScrollState()

    val addNotificationScreen = notificationModel == NotificationModel()

    var unseenCheck by rememberSaveable {
        mutableStateOf(false)
    }

    val calender = Calendar.getInstance()

    val year = calender.get(Calendar.YEAR)
    val month = (calender.get(Calendar.MONTH) + 1).addZeroToStart()
    val day = calender.get(Calendar.DAY_OF_MONTH).addZeroToStart()

    val hour = calender.get(Calendar.HOUR_OF_DAY).addZeroToStart()
    val minute = calender.get(Calendar.MINUTE).addZeroToStart()
    val second = calender.get(Calendar.SECOND).addZeroToStart()

    val time = "$year:$month:$day:$hour:$minute:$second"

    var unseenCheckClicked by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val coordinates = LocalConfiguration.current

    val screenHeight = coordinates.screenHeightDp.dp

    Scaffold(topBar = {

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = TOP_APP_BAR_ELEVATION,
            color = ExtraDark
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TOP_APP_BAR_HEIGHT)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if (addNotificationScreen) Arrangement.Start else Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            modifier = Modifier
                                .width(28.dp)
                                .height(24.dp),
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "back_arrow",
                            tint = UIWhite
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = if (addNotificationScreen) "Add Notification" else "Notification",
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = LARGE_TEXT_SIZE,
                        color = UIWhite,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    if (!addNotificationScreen) {
                        IconButton(onClick = {
                            onDeleteClicked(notificationModel)
                        }) {
                            Icon(
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(24.dp),
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "back_arrow",
                                tint = UIWhite
                            )
                        }
                    }
                }

            }

        }

    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Dark)
                .verticalScroll(scrollState)
                .padding(top = 50.dp, start = 30.dp, end = 30.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {
                // group of Name textField
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 3.dp, bottom = 2.dp),
                        text = "Name*",
                        fontSize = TEXT_FIELD_SIZE,
                        color = UIWhite,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .border(
                                width = 1.8.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        value = name,
                        onValueChange = { text ->
                            name = text.convertStringToAlphabets(length = 50)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = UIWhite,
                        ),
                        textStyle = TextStyle(
                            color = UIWhite,
                            fontSize = TEXT_FIELD_SIZE
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true
                    )
                }

                // group of Message textField
                Column(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 3.dp, bottom = 2.dp),
                        text = "Message*",
                        fontSize = TEXT_FIELD_SIZE,
                        color = UIWhite,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight/3)
                            .border(
                                width = 1.8.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        value = message,
                        onValueChange = { text ->
                            message = text.convertStringToAlphabets(length = 1000)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = UIWhite,
                        ),
                        textStyle = TextStyle(
                            color = UIWhite,
                            fontSize = TEXT_FIELD_SIZE
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                        }),
                        singleLine = false
                    )
                }

                if (!unseenCheck) {
                    if (notificationModel.status == NotificationStatus.SEEN.name && !unseenCheckClicked) {
                        Text(
                            modifier = Modifier.padding(top = 30.dp),
                            text = notificationModel.timeSeen.timeStringToDate(),
                            color = UIWhite,
                            fontFamily = fontInter,
                            fontSize = TEXT_FIELD_SIZE
                        )
                    } else if (!addNotificationScreen || unseenCheckClicked) {
                        Text(
                            modifier = Modifier.padding(top = 30.dp),
                            text = "seen just now",
                            color = UIWhite,
                            fontFamily = fontInter,
                            fontSize = TEXT_FIELD_SIZE
                        )
                    }
                }

                if (notificationModel.status == NotificationStatus.SEEN.name || !addNotificationScreen) {

                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(
                                    shape = RoundedCornerShape(15),
                                    color = if (unseenCheck) UIRed else Color.Transparent
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (unseenCheck) UIRed else Color.Gray,
                                    shape = RoundedCornerShape(15)
                                )
                                .clickable {
                                    unseenCheck = !unseenCheck

                                    unseenCheckClicked = true

                                    sharedViewModel.updateNotification(
                                        notificationModel = NotificationModel(
                                            id = notificationModel.id,
                                            name = notificationModel.name,
                                            message = notificationModel.message,
                                            status = if (unseenCheck) NotificationStatus.UNSEEN.name else NotificationStatus.SEEN.name,
                                            timeSeen = time
                                        )
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {

                            if (unseenCheck) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = null,
                                    tint = UIWhite
                                )
                            }

                        }

                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = "Unseen this Notification",
                            fontSize = MEDIUM_TEXT_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = UIWhite
                        )

                    }

                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(200.dp)
                        .height(45.dp),
                    shape = CircleShape,
                    elevation = 4.dp, color = Color.Transparent,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush = buttonColor)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {

                                    if (name.isNotEmpty() && message.isNotEmpty()) {
                                        onSaveClicked(
                                            NotificationModel(
                                                name = name,
                                                message = message,
                                                status = if (unseenCheck) NotificationStatus.UNSEEN.name else NotificationStatus.SEEN.name,
                                                timeSeen = time
                                            )
                                        )
                                    } else {
                                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                                    }

                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (addNotificationScreen) "Add" else "Save",
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            fontSize = LARGE_TEXT_SIZE,
                            color = Color.White
                        )
                    }
                }

            }
        }

    }
}