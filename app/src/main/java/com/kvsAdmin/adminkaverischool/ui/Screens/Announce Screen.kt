package com.kvsAdmin.adminkaverischool.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kvsAdmin.adminkaverischool.R
import com.kvsAdmin.adminkaverischool.data.ToggleableInfo
import com.kvsAdmin.adminkaverischool.navigation.DestinationScreen
import com.kvsAdmin.adminkaverischool.states.onError
import com.kvsAdmin.adminkaverischool.ui.adminKvsViewModel
import com.kvsAdmin.adminkaverischool.ui.theme.hex
import com.kvsAdmin.util.OnErrorMessage
import com.kvsAdmin.util.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnounceScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: adminKvsViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 38.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = hex,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {

        }
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(it)
                .fillMaxHeight()
                .fillMaxSize(),
        ) {
            Image(
                alpha = 0.40f,
                painter = painterResource(id = R.drawable.whatsapp_kaveri),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = CardDefaults.outlinedShape,
                colors = CardDefaults.cardColors(hex)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .verticalScroll(rememberScrollState())
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    val announcementState = remember {
                        mutableStateOf("")
                    }
                    val classState = remember {
                        mutableStateOf("")
                    }
                    val sectionState = remember {
                        mutableStateOf("A")
                    }

                    Text(
                        text = "Select Class",
                        fontSize = 25.sp,
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                    val radioButtons = remember {
                        mutableStateListOf(
                            ToggleableInfo(

                                isChecked = false,
                                text = "PreKG"
                            ),
                            ToggleableInfo(

                                isChecked = false,
                                text = "LKG"
                            ),
                            ToggleableInfo(

                                isChecked = false,
                                text = "UKG"
                            ),
                            ToggleableInfo(

                                isChecked = false,
                                text = "1"
                            ),
                            ToggleableInfo(
                                isChecked = false,
                                text = "2"
                            ),
                            ToggleableInfo(
                                isChecked = false,
                                text = "3"
                            ),
                            ToggleableInfo(
                                isChecked = false,
                                text = "4"
                            ),
                            ToggleableInfo(
                                isChecked = false,
                                text = "5"
                            ),
                            ToggleableInfo(
                                isChecked = false,
                                text = "6"
                            ),
                            ToggleableInfo(
                                isChecked = false,
                                text = "7"
                            ),
                            ToggleableInfo(
                                isChecked = false,
                                text = "8"
                            ),
                            ToggleableInfo(
                                isChecked = false,
                                text = "9"
                            ),
                            ToggleableInfo(
                                isChecked = false,
                                text = "10"
                            ),

                            )
                    }
                    radioButtons.forEachIndexed { index, info ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    radioButtons.replaceAll {
                                        it.copy(
                                            isChecked = it.text == info.text
                                        )
                                    }
                                }
                                .padding(end = 16.dp)
                        ) {

                            RadioButton(
                                selected = info.isChecked,
                                onClick = {
                                    radioButtons.replaceAll {
                                        it.copy(
                                            isChecked = it.text == info.text
                                        )
                                    }
                                    classState.value = info.text
                                }
                            )
                            Text(
                                text = info.text,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold

                            )
                        }

                    }
                    Spacer(modifier = modifier.padding(16.dp))

                    Text(
                        text = "Announcement",
                        modifier = Modifier
                            .padding(8.dp),
                        fontSize = 25.sp,
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        OutlinedTextField(
                            value = announcementState.value,
                            onValueChange = {
                                announcementState.value = it
                            },
                            label = {
                                Text(
                                    text = "Message",
                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "Make An Announcement",
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                            },
                            colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.primary)
                        )

                    }
                    Spacer(modifier = modifier.padding(16.dp))

                    if (onError.value){
                        OnErrorMessage()
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                viewModel.announcement(
                                    section = sectionState.value.trim(),
                                    classroom = classState.value.trim(),
                                    text = announcementState.value
                                )
                                navigateTo(navController,DestinationScreen.HomeScreen.route)
                            }
                        ) {
                            Text(
                                text = "POST",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}