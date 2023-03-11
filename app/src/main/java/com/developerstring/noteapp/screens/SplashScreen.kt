package com.developerstring.noteapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.noteapp.SharedViewModel
import com.developerstring.noteapp.navigation.NavRoute
import com.developerstring.noteapp.ui.theme.*
import com.developerstring.noteapp.util.Constants.notificationList
import com.developerstring.noteapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    SplashScreenContent()

    val context = LocalContext.current

    sharedViewModel.getOnBoardingStatus(context = context)
    val onBoardingStatus by sharedViewModel.onBoardingStatus.collectAsState()

    if (onBoardingStatus == "NO") {
        notificationList.forEach {
            sharedViewModel.addNotification(it)
        }
        sharedViewModel.saveOnBoardingStatus(context = context)
    }

    LaunchedEffect(key1 = true) {
        delay(2500)
        navController.popBackStack()

        navController.navigate(route = NavRoute.MainScreenRoute.route)
    }


}

@Composable
fun SplashScreenContent() {
    var animated by remember {
        mutableStateOf(false)
    }
    var animatedLater by remember {
        mutableStateOf(false)
    }

    val animatedSize by animateDpAsState(
        targetValue = if (animated) 160.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 800,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animated = true
        delay(500)
        animatedLater = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Dark),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    modifier = Modifier.size(animatedSize),
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }

            AnimatedVisibility(visible = animatedLater) {
                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontInter,
                    fontSize = EXTRA_LARGE_TEXT_SIZE,
                    color = UIWhite
                )
            }

        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = UIRed,
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.11f))
        }
    }
}