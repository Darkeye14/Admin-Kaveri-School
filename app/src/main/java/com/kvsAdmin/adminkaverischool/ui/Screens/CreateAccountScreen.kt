package com.kvsAdmin.adminkaverischool.ui.Screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kvsAdmin.adminkaverischool.R
import com.kvsAdmin.adminkaverischool.states.inProgress
import com.kvsAdmin.adminkaverischool.states.toastState
import com.kvsAdmin.adminkaverischool.ui.adminKvsViewModel
import com.kvsAdmin.adminkaverischool.ui.theme.hex
import com.kvsAdmin.util.CommonProgressBar
import com.kvsAdmin.util.OnToastMessage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
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
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
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


            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(color = Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val emailState = remember {
                    mutableStateOf("")
                }
                val classState = remember {
                    mutableStateOf("")
                }
                val sectionState = remember {
                    mutableStateOf("A")
                }
                val passwordState = remember {
                    mutableStateOf("")
                }
                val studentNameState = remember {
                    mutableStateOf("")
                }
                val parentsNameState = remember {
                    mutableStateOf("")
                }
                val parentsPhoneState = remember {
                    mutableStateOf("")
                }

                val showAlert = remember { mutableStateOf(false) }




                Text(
                    text = "Create Account",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )

                if (showAlert.value) {
                    classState.value = SelectClass()
                }


                OutlinedTextField(
                    colors = TextFieldDefaults.colors(hex),
                    value = emailState.value,
                    singleLine = true,
                    onValueChange = {
                        emailState.value = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(text = "Ex: abcd@gmail.com")
                    },
                    label = {
                        Text(
                            text = "Email",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )

                OutlinedTextField(
                    colors = TextFieldDefaults.colors(hex),
                    value = passwordState.value,
                    singleLine = true,
                    onValueChange = {
                        passwordState.value = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    label = {
                        Text(
                            text = "Password (Alphanumeric)",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    },
                    placeholder = {
                        Text(text = "Ex: abcd1234")
                    }
                )

                OutlinedTextField(
                    colors = TextFieldDefaults.colors(hex),
                    value = studentNameState.value,
                    singleLine = true,
                    onValueChange = {
                        studentNameState.value = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    label = {
                        Text(
                            text = "Student Name",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )

                if (toastState.value) {
                    OnToastMessage()
                }
                if (classState.value !=""){
                    showAlert.value = false
                }

                OutlinedTextField(
                    colors = TextFieldDefaults.colors(hex),
                    value = parentsNameState.value,
                    singleLine = true,
                    onValueChange = {
                        parentsNameState.value = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    label = {
                        Text(
                            text = "Parent's Name",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )
                OutlinedTextField(
                    colors = TextFieldDefaults.colors(hex),
                    value = parentsPhoneState.value,
                    singleLine = true,
                    onValueChange = {
                        parentsPhoneState.value = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    label = {
                        Text(
                            text = "Parent's Number",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )
                Button(
                    onClick = {
                        showAlert.value = true
                    }
                ) {
                    Text(text = "Choose Class")
                }

                Button(
                    onClick = {
                        if (
                            classState.value == "" ||
                            emailState.value == "" ||
                            passwordState.value == ""

                        ) {
                            toastState.value = true

                        } else {
                            viewModel.signUp1(
                                studentNameState.value.trim(),
                                parentsNameState.value.trim(),
                                parentsPhoneState.value.trim(),
                                classState.value.trim(),
                                emailState.value.trim(),
                                passwordState.value.trim(),
                                sectionState.value.trim(),
                                navController
                            )
                        }


                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {

                    Text(text = "CREATE ACCOUNT")
                }

            }
            if (inProgress.value) {
                CommonProgressBar()
            }
        }
    }
}

