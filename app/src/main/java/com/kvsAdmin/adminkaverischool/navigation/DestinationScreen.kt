package com.kvsAdmin.adminkaverischool.navigation

sealed class DestinationScreen(var route : String) {

    data object LoginScreen :DestinationScreen("loginScreen")
    data object HomeScreen :DestinationScreen("homeScreen")
    data object EventPostsScreen :DestinationScreen("EventPostsScreen")
    data object AnnounceScreen :DestinationScreen("announceScreen")


}