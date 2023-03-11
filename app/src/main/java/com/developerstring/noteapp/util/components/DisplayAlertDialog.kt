package com.developerstring.noteapp.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.developerstring.noteapp.ui.theme.*
import com.developerstring.noteapp.util.convertStringToAlphabets
import com.developerstring.noteapp.util.randomCaptcha

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    captchaVerification: Boolean = false,
    onCloseClicked: () -> Unit,
    onYesClicked: () -> Unit,
) {

    var confirmEnable by remember {
        mutableStateOf(!captchaVerification)
    }

    var captchaEntered by remember {
        mutableStateOf("")
    }

    var captchaValue by rememberSaveable {
        mutableStateOf(randomCaptcha(6))
    }

    fun reCaptcha(){
        captchaEntered = ""
        captchaValue = randomCaptcha(6)
        confirmEnable = false
    }

    if (openDialog) {
        AlertDialog(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            backgroundColor = ExtraDark,
            shape = RoundedCornerShape(10),
            title = {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = title,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Bold,
                    fontSize = LARGE_TEXT_SIZE,
                    color = UIWhite
                )
            },
            text = {

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = message,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = MEDIUM_TEXT_SIZE,
                        color = UIWhite
                    )

                    if (captchaVerification) {

                        Column(modifier = Modifier.fillMaxWidth()) {

                            Box(
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 10.dp)
                                    .fillMaxWidth()
                                    .background(color = Color.Black, shape = RoundedCornerShape(3.dp))
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center

                            ) {
                                Text(
                                    text = captchaValue,
                                    fontFamily = fontInter,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = TEXT_FIELD_SIZE,
                                    color = UIWhite
                                )
                            }

                            // group of Captcha textField
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(start = 3.dp, bottom = 2.dp),
                                    text = "Captcha",
                                    fontSize = TEXT_FIELD_SIZE,
                                    color = UIWhite,
                                    fontFamily = fontInter,
                                    fontWeight = FontWeight.Medium
                                )

                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(55.dp)
                                        .border(
                                            width = 1.8.dp,
                                            color = UIWhite,
                                            shape = RoundedCornerShape(15.dp)
                                        ),
                                    value = captchaEntered,
                                    onValueChange = {
                                        captchaEntered = it.convertStringToAlphabets(10)
                                        confirmEnable = captchaValue == captchaEntered
                                    },
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = UIWhite,
                                        disabledIndicatorColor = Color.Transparent
                                    ),
                                    textStyle = TextStyle(
                                        color = UIWhite,
                                        fontSize = TEXT_FIELD_SIZE
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
                                    ),
                                    singleLine = true
                                )
                            }


                        }

                    }
                }


            },
            confirmButton = {
                Button(
                    modifier = Modifier.padding(end = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor =
                        if (confirmEnable) LightUIRed
                        else UIWhite.copy(alpha = 0.4f)
                    ),
                    shape = CircleShape,
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {
                        if (confirmEnable) {
                            reCaptcha()
                            onYesClicked()
                            onCloseClicked()
                        }
                    }
                ) {
                    Text(
                        text = "YES",
                        color = if (confirmEnable) {
                            UIWhite
                        } else Color.Black.copy(alpha = 0.4f),
                        fontSize = LARGE_TEXT_SIZE,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier.padding(end = 10.dp, bottom = 30.dp),
                    onClick = {
                        reCaptcha()
                        onCloseClicked()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    shape = CircleShape,
                    elevation = ButtonDefaults.elevation(0.dp)
                ) {
                    Text(
                        text = "NO",
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Bold,
                        fontSize = LARGE_TEXT_SIZE,
                        color = UIWhite
                    )
                }
            },
            onDismissRequest = {
                onCloseClicked()
            }
        )
    }

}