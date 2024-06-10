package com.kvsAdmin.adminkaverischool.data

import android.net.Uri

data class ToggleableInfo(
    val isChecked: Boolean,
    val text : String
)

data class Announcement(
    val classroom : String ?="",
    val section : String?="",
    val text : String?="",
    val sortTime : String?="",
    val timeStamp :String?="",
)
data class AllPicsUploadList(
    val uid: String?= ""
)
data class addingPost(
    val title :String? = "",
    val disc :String ? = "",
    val imageUidList : List<String?> ?= listOf(),
    val uid :String?= "",
    val sortTime : String?="",
    val timeStamp :String?="",
)
data class recievingPost(
    val title :String? = "",
    val disc :String ? = "",
    val imageUidList : List<String?> ?= listOf(),
    val uid :String?= "",
    val sortTime : String?="",
    val timeStamp :String?="",
)