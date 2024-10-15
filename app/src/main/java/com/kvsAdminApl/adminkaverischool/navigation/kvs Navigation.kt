package com.kvsAdminApl.adminkaverischool.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kvsAdminApl.adminkaverischool.ui.Screens.AnnounceScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.DeletePicsScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.EventPostsScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.HomeScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.LoginScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.ManageAnnouncementScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.ManagePostsScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.PicsScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.SignUpScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.SinglePostScreen
import com.kvsAdminApl.adminkaverischool.ui.Screens.ViewAllPicsScreen
import com.kvsAdminApl.adminkaverischool.ui.adminKvsViewModel

@Composable
fun KvsNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<adminKvsViewModel>()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.LoginScreen.route
    ){
        composable(DestinationScreen.LoginScreen.route){
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.CreateAccScreen.route){
            SignUpScreen(navController = navController, viewModel = viewModel)
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
            ViewAllPicsScreen(navController = navController, viewModel = viewModel)
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
        composable(DestinationScreen.DeletePicsScreen.route){
            val uid = it.arguments?.getString("uid")
            val type = it.arguments?.getString("type")

            DeletePicsScreen(type = type!!, uid = uid, viewModel =viewModel, navController = navController )
        }
        composable(DestinationScreen.SinglePostScreen.route){
            val postId = it.arguments?.getString("postId")
            postId?.let {
                SinglePostScreen(postId,navController,viewModel)
            }

        }
    }
}