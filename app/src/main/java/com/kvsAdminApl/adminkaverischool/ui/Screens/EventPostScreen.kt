package com.kvsAdminApl.adminkaverischool.ui.Screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kvsAdminApl.adminkaverischool.R
import com.kvsAdminApl.adminkaverischool.navigation.DestinationScreen
import com.kvsAdminApl.adminkaverischool.ui.adminKvsViewModel
import com.kvsAdminApl.adminkaverischool.ui.theme.hex
import com.kvsAdminApl.util.ProfileImage
import com.kvsAdminApl.util.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventPostsScreen(
    navController: NavController,
    viewModel: adminKvsViewModel,
    modifier: Modifier = Modifier
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

            val imageState = remember {
                mutableStateOf(false)
            }
            val titleState = remember {
                mutableStateOf("")
            }
            val descriptionState = remember {
                mutableStateOf("")
            }
            val uriState = remember {
                mutableStateOf<List<Uri?>>(listOf())
            }
            Image(
                alpha = 0.40f,
                painter = painterResource(id = R.drawable.whatsapp_kaveri),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(20.dp),
                    shape = CardDefaults.outlinedShape,
                    colors = CardDefaults.cardColors(hex),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center,
                            text = "Create A Post",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        OutlinedTextField(
                            value = titleState.value,
                            onValueChange = {
                                titleState.value = it
                            },
                            label = {
                                Text(
                                    text = "Title",
                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                            },
                            colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        OutlinedTextField(
                            value = descriptionState.value,
                            onValueChange = {
                                descriptionState.value = it
                            },
                            label = {
                                Text(
                                    text = "Full Description",
                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "Add all other information",
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                            },
                            colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))


                        Button(
                            onClick = {
                                imageState.value = true
                            }
                        ) {
                            Text(text = "Upload Images")
                        }
                        if (imageState.value){
                                   uriState.value = ProfileImage()
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Button(
                            onClick = {
                                viewModel.onPost(uriState.value,titleState.value,descriptionState.value)
                                navigateTo(navController,DestinationScreen.HomeScreen.route)
                            }
                        ) {
                            Text(text = "Create Post")
                        }
                        Spacer(modifier = Modifier.padding(20.dp))
                    }
                }
            }
        }
    }
}

