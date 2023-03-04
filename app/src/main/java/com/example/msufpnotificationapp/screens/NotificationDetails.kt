package com.example.msufpnotificationapp.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.msufpnotificationapp.util.components.DisplayAlertDialog
import com.example.msufpnotificationapp.util.convertStringToAlphabets
import com.example.msufpnotificationapp.util.state.NotificationAction

@Composable
fun NotificationDetails(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    val notificationsModel = sharedViewModel.notificationModel.value

//    Toast.makeText(LocalContext.current, notificationsModel.id.toString(), Toast.LENGTH_SHORT).show()

    val scrollState = rememberScrollState()

    var deleteClicked by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (deleteClicked) {
            DisplayAlertDialog(
                title = "Sure to Delete this notification",
                message = "You are just one step away form deleting this notifications",
                openDialog = true,
                onCloseClicked = {
                    deleteClicked = false
                },
                onYesClicked = {
                    sharedViewModel.notificationAction.value = NotificationAction.DELETE
                    sharedViewModel.notificationAction(NotificationAction.DELETE)
                    navController.popBackStack()
                    deleteClicked = false
                },
                captchaVerification = false
            )
        }

        AddNotificationScreenContent(
            navController = navController,
            notificationModel = notificationsModel,
            sharedViewModel = sharedViewModel,
            onSaveClicked = {
                sharedViewModel.updateNotification(
                    NotificationModel(
                        id = notificationsModel.id,
                        name = it.name,
                        message = it.message,
                        status = it.status,
                        timeSeen = it.timeSeen
                    )
                )
                navController.popBackStack()
            },
            onDeleteClicked = {

                deleteClicked = true

            }
        )


    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotificationDetailContent(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    notificationModel: NotificationModel,
    onDeleteClicked: (NotificationModel) -> Unit,
    onSaveClicked: (NotificationModel) -> Unit
) {

    var name by rememberSaveable {
        mutableStateOf(notificationModel.name)
    }

    var message by rememberSaveable {
        mutableStateOf(notificationModel.message)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(topBar = {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT)
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                text = notificationModel.name,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = LARGE_TEXT_SIZE,
                color = UIWhite,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )


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

    }) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .padding(top = 50.dp)
        ) {

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
                        .heightIn(min = 200.dp)
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

        }
    }

}