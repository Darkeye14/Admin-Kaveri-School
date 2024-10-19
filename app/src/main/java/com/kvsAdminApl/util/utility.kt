package com.kvsAdminApl.util

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kvsAdminApl.adminkaverischool.data.Announcement
import com.kvsAdminApl.adminkaverischool.data.recievingPost
import com.kvsAdminApl.adminkaverischool.navigation.DestinationScreen
import com.kvsAdminApl.adminkaverischool.states.errorMsg
import com.kvsAdminApl.adminkaverischool.states.toastState
import com.kvsAdminApl.adminkaverischool.ui.Screens.DeleteAnnouncementButton
import com.kvsAdminApl.adminkaverischool.ui.adminKvsViewModel
import com.kvsAdminApl.adminkaverischool.ui.theme.hex

fun navigateTo(
    navController: NavController,
    route: String
) {
    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}

@Composable
fun CommonProgressBar() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun OnErrorMessage(
    modifier: Modifier = Modifier
) {
    Toast.makeText(LocalContext.current, errorMsg.value, Toast.LENGTH_SHORT).show()
}

@Composable
fun TextCard(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight,
    fontSize: Int
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.cardColors(hex)
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                text = text,
                color = Color.White,
                fontSize = fontSize.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = fontWeight,
            )
        }
    }
}

@Composable
fun AnnouncementsCard(
    post: Announcement,
    viewModel: adminKvsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        colors = CardDefaults.cardColors(hex)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
        ) {

            Text(
                text = "     Attention!!",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                color = Color.White,
                modifier = Modifier
                    .padding(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = post.text ?: "Empty",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(12.dp)
                )

                Spacer(modifier = Modifier.padding(10.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                DeleteAnnouncementButton {
                    viewModel.onDeleteAnnouncement(post.uid!!,post.classroom!!)
                    navigateTo(navController,DestinationScreen.HomeScreen.route)
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = post.timeStamp ?: "recently",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(12.dp)
                )

                Spacer(modifier = Modifier.padding(10.dp))
            }

        }
    }
}


@Composable
fun PostCard(
    post: recievingPost,
    onItemClick: () -> Unit,
    asyncImages: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
            .clickable {
                onItemClick.invoke()
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),


            ) {
            asyncImages.invoke()

            Text(
                text = post.title ?: "     Attention!!",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = post.timeStamp ?: "recently",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(12.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }

        }
    }
}


@Composable
fun ProfileImage() : List<Uri?> {

    var uriState by remember { mutableStateOf<List<Uri?>>(listOf()) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) {
        uriState = it
    }

    Button(
        onClick = { launcher.launch("image/jpeg") },
    ) {
        Text(text = "Choose Photos", color = Color.White, style = MaterialTheme.typography.bodySmall)
    }
    Spacer(modifier = Modifier.padding(8.dp))
    return uriState
}

@Composable
fun OnToastMessage() {
    if (toastState.value)
        Toast.makeText(LocalContext.current, "Please Fill All Fields", Toast.LENGTH_LONG).show()
    toastState.value = false
}


