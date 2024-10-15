package com.kvsAdminApl.adminkaverischool.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.kvsAdminApl.adminkaverischool.R
import com.kvsAdminApl.adminkaverischool.data.ToggleableInfo
import com.kvsAdminApl.adminkaverischool.states.announcementsDataList
import com.kvsAdminApl.adminkaverischool.states.imageUriList
import com.kvsAdminApl.adminkaverischool.states.onError
import com.kvsAdminApl.adminkaverischool.ui.adminKvsViewModel
import com.kvsAdminApl.adminkaverischool.ui.theme.hex
import com.kvsAdminApl.util.AnnouncementsCard
import com.kvsAdminApl.util.OnErrorMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAnnouncementScreen(
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
        val announcements = announcementsDataList.value
        val classNo =   remember{
        mutableStateOf("")
    }
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
            if (onError.value){
                OnErrorMessage()
            }
            if (announcements.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(color = Color.Transparent),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    classNo.value = SelectClass()
                    if (classNo.value != ""){
                        viewModel.populateAnnouncement(classNo.value)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(color = Color.Transparent)
                ) {
                    item {
                        if (classNo.value == "") {
                            classNo.value = SelectClass()
                        }
                        if (classNo.value != ""){
                            viewModel.populateAnnouncement(classNo.value)
                        }
                    }
                    items(announcements) { post ->
                        imageUriList.clear()
                        AnnouncementsCard(post = post, viewModel= viewModel, navController = navController)
                    }

                }
            }
        }

    }
}

@Composable
fun SelectClass(): String {
    val classState = remember{
        mutableStateOf("")
    }
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

    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text(text = "OK")
                }
            },
            title = {
                Text(text = "Select Class",
                    color =Color.White,
                    fontWeight = FontWeight.Bold)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            containerColor = hex,
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {


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
                }

            },
        )
    }
    return classState.value
}


@Composable
fun DeleteAnnouncementButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(Icons.Default.Delete, "Extended floating action button.") },
        text = { Text(text = "Delete Post") },
        containerColor = Color.LightGray
    )
}