package com.developerstring.noteapp.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.developerstring.noteapp.R
import com.developerstring.noteapp.database.NotificationModel
import com.developerstring.noteapp.ui.theme.*
import com.developerstring.noteapp.util.state.NotificationStatus

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NotificationHistoryItem(
    notificationModel: NotificationModel,
    onCLicked: (NotificationModel) -> Unit
) {

    val unseen = notificationModel.status == NotificationStatus.UNSEEN.name

    val cardColor = Brush.horizontalGradient(colors = listOf(Yellow, Pink, UIRed))

    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        expanded = true
    }

    AnimatedVisibility(
        visible = expanded,
        enter = scaleIn() + expandVertically(
            expandFrom = Alignment.CenterVertically,
            animationSpec = tween(durationMillis = 1000)
        ),

        ) {

        Surface(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 10.dp)
                .fillMaxWidth()
                .clickable(

                ) {
                    onCLicked(notificationModel)
                },
            shape = RoundedCornerShape(10.dp),
            color = DarkerBlue,
            elevation = 10.dp
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .background(brush = cardColor, shape = CircleShape),
                ) {

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = notificationModel.name.first().uppercase(),
                            fontSize = EXTRA_LARGE_TEXT_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Bold,
                            color = UIWhite,
                            textAlign = TextAlign.Center
                        )
                    }


                }

                Row(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                    ) {
                        Text(
                            text = notificationModel.name,
                            fontSize = if (unseen) LARGE_TEXT_SIZE else MEDIUM_TEXT_SIZE,
                            fontWeight =
                            if (unseen) FontWeight.Bold
                            else FontWeight.Medium,
                            fontFamily = if (unseen) fontPoppins
                            else fontInter,
                            color = UIWhite,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = notificationModel.message,
                            fontSize = if (unseen) MEDIUM_TEXT_SIZE else SMALL_TEXT_SIZE,
                            fontWeight =
                            if (unseen) FontWeight.Bold
                            else FontWeight.Medium,
                            fontFamily = if (unseen) fontPoppins
                            else fontInter,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }


                    if (unseen) {
                        Box(
                            modifier = Modifier
                                .size(25.dp)
                                .background(color = UIRed, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(text = "1", color = UIWhite, fontSize = SMALL_TEXT_SIZE)

                        }
                    } else {


                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = R.drawable.icon_done_all),
                            contentDescription = null,
                            tint = UIBlue
                        )


                    }


                }

            }

        }
    }

}