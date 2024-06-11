package com.kvsAdmin.adminkaverischool.navigation

sealed class DestinationScreen(var route : String) {

    data object LoginScreen :DestinationScreen("loginScreen")
    data object ManageAnnouncementsScreen :DestinationScreen("manageAnnounceScreen")
    data object HomeScreen :DestinationScreen("homeScreen")
    data object EventPostsScreen :DestinationScreen("EventPostsScreen")
    data object AnnounceScreen :DestinationScreen("announceScreen")
    data object ManagePostsScreen :DestinationScreen("managePostsScreen")
    data object AllImagesScreen :DestinationScreen("allImagesScreen")
    data object SinglePostScreen:DestinationScreen("singlePostScreen/{postId}"){
        fun createRoute(id : String) = "singlePostScreen/$id"
    }

}