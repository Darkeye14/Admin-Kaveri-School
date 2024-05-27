package com.kvsAdmin.adminkaverischool.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kvsAdmin.adminkaverischool.ui.Screens.AnnounceScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.HomeScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.LoginScreen
import com.kvsAdmin.adminkaverischool.ui.Screens.PostsScreen
import com.kvsAdmin.adminkaverischool.ui.adminKvsViewModel

@Composable
fun KvsNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<adminKvsViewModel>()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.PostsScreen.route
    ){
        composable(DestinationScreen.LoginScreen.route){
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.HomeScreen.route){
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.AnnounceScreen.route){
            AnnounceScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.PostsScreen.route){
            PostsScreen(navController = navController, viewModel = viewModel)
        }

    }
}