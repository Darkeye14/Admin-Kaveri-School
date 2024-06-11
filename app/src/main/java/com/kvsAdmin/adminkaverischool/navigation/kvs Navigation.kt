package com.kvsAdmin.adminkaverischool.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kvsAdmin.adminkaverischool.ui.Screens.AnnounceScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.EventPostsScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.HomeScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.LoginScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.ManageAnnouncementScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.ManagePostsScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.PicsScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.SinglePostScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.ViewAllPicsScreen
import com.kvsAdmin.adminkaverischool.ui.adminKvsViewModel

@Composable
fun KvsNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<adminKvsViewModel>()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.HomeScreen.route
    ){
        composable(DestinationScreen.LoginScreen.route){
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.ManageAnnouncementsScreen.route){
            ManageAnnouncementScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.AllImagesScreen.route){
            PicsScreen( navController = navController,viewModel = viewModel)
        }
        composable(DestinationScreen.HomeScreen.route){
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.ManageAllPhotosScreen.route){
            ViewAllPicsScreen(viewModel = viewModel)
        }
        composable(DestinationScreen.AnnounceScreen.route){
            AnnounceScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.EventPostsScreen.route){
            EventPostsScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.ManagePostsScreen.route){
            ManagePostsScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.SinglePostScreen.route){
            val postId = it.arguments?.getString("postId")
            postId?.let {
                SinglePostScreen(postId,navController,viewModel)
            }

        }
    }
}