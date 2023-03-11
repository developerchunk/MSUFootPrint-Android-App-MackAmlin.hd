package com.developerstring.noteapp.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.developerstring.noteapp.SharedViewModel
import com.developerstring.noteapp.ui.theme.*
import com.developerstring.noteapp.util.Constants.TopAppBarMenuList
import com.developerstring.noteapp.util.state.SearchBarState
import com.developerstring.noteapp.util.state.TrailingIconStateSearch
import com.developerstring.noteapp.R

@Composable
fun TopAppBarHistory(
    sharedViewModel: SharedViewModel,
    searchBarState: SearchBarState,
    searchBarText: String,
    onMenuCLicked: (String) -> Unit
) {

    LaunchedEffect(key1 = true) {
        sharedViewModel.searchBarState.value = SearchBarState.DELETE
        sharedViewModel.searchBarText.value = ""
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = ExtraDark
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(bottom = 10.dp)
        ) {

            when (searchBarState) {
                SearchBarState.DELETE -> {
                    DefaultTopAppBarHistory(
                        sharedViewModel = sharedViewModel,
                        onMenuCLicked = {menu ->
                            onMenuCLicked(menu)
                        }
                    )
                }

                else -> {
                    SearchedTopAppBarHistory(
                        text = searchBarText,
                        onTextChange = {
                            sharedViewModel.searchBarText.value = it
                        },
                        onSearchClicked = {
                            sharedViewModel.searchBarState.value = SearchBarState.TRIGGERED
                            sharedViewModel.getSearchedNotification(searchQuery = "%$searchBarText%")
                        },
                        onCloseClicked = {
                            sharedViewModel.searchBarText.value = ""

                            sharedViewModel.searchBarState.value = SearchBarState.DELETE
                        },
                    )
                }
            }


        }
    }

}

@Composable
fun DefaultTopAppBarHistory(
    sharedViewModel: SharedViewModel,
    onMenuCLicked: (String) -> Unit
) {

    var selectedMenu by rememberSaveable {
        mutableStateOf("")
    }

    var menuExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT)
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            fontFamily = fontInter,
            fontWeight = FontWeight.Medium,
            fontSize = LARGE_TEXT_SIZE,
            color = UIWhite
        )

        Row {
            IconButton(onClick = {
                sharedViewModel.searchBarState.value = SearchBarState.OPENED
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "search",
                    tint = UIWhite
                )
            }

            IconButton(modifier = Modifier.padding(start = 10.dp), onClick = {

                menuExpanded = !menuExpanded

            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "more",
                    tint = UIWhite
                )

                DropdownMenu(
                    modifier = Modifier.background(SlightGray),
                    expanded = menuExpanded,
                    onDismissRequest = {
                        menuExpanded = false
                    }
                ) {
                    TopAppBarMenuList.forEach {
                        DropdownMenuItem(onClick = {
                            selectedMenu = it
                            menuExpanded = false
                            onMenuCLicked(selectedMenu)
                        }) {
                            Text(
                                text = it,
                                fontSize = MEDIUM_TEXT_SIZE,
                                fontFamily = fontInter,
                                fontWeight = FontWeight.Medium,
                                color = UIWhite
                            )

                        }
                    }

                }
            }
        }

    }

}

@Composable
fun SearchedTopAppBarHistory(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {

    var trailingIconStateSearch by remember {
        mutableStateOf(TrailingIconStateSearch.DELETE_TEXT)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search Here",
                    color = UIWhite,
                    fontSize = TEXT_FIELD_SIZE
                )
            },
            textStyle = TextStyle(
                color = UIWhite,
                fontSize = TEXT_FIELD_SIZE
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        onSearchClicked(text)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = UIWhite
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {

                        when (trailingIconStateSearch) {
                            TrailingIconStateSearch.DELETE_TEXT -> {

                                trailingIconStateSearch = if (text.isNotEmpty()) {
                                    onTextChange("")
                                    TrailingIconStateSearch.CLOSE_TOP_BAR
                                } else {
                                    onCloseClicked()
                                    TrailingIconStateSearch.DELETE_TEXT
                                }
                            }
                            TrailingIconStateSearch.CLOSE_TOP_BAR -> {
                                if (text.isNotEmpty()) {
                                    onTextChange("")
                                } else {
                                    onCloseClicked()
                                    trailingIconStateSearch = TrailingIconStateSearch.DELETE_TEXT
                                }
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = UIWhite
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = UIWhite,
                focusedIndicatorColor = UIWhite,
                disabledIndicatorColor = UIWhite,
                unfocusedIndicatorColor = UIWhite,
                backgroundColor = Color.Transparent
            )
        )

    }

}